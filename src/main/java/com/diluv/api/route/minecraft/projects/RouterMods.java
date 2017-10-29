package com.diluv.api.route.minecraft.projects;

import com.diluv.api.error.ErrorMessages;
import com.diluv.api.error.Errors;
import com.diluv.api.jwt.JWT;
import com.diluv.api.models.tables.records.ProjectRecord;
import com.diluv.api.utils.*;
import com.diluv.api.utils.page.Page;
import com.diluv.api.utils.page.PagesUtilities;
import com.github.slugify.Slugify;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.impl.RouterImpl;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.TableField;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.diluv.api.models.Tables.PROJECT;

public class RouterMods extends RouterImpl {
    private final Connection conn;
    private final Vertx vertx;
    private final Long projectTypeId;

    public RouterMods(Connection conn, Vertx vertx) {
        super(vertx);
        this.conn = conn;
        this.vertx = vertx;

        this.projectTypeId = ProjectTypeUtilities.getProjectTypeIdBySlug(this.conn, "mods");
        this.get("/").handler(this::getMods);
        this.get("/projects").handler(this::getProjectsByMods);
        this.get("/projects/:projectSlug").handler(this::getProjectBySlug);
        this.get("/projects/:projectSlug/members").handler(this::getProjectMembersByProjectSlug);
        this.get("/projects/:projectSlug/files").handler(this::getProjectFiles);
        this.get("/projects/:projectSlug/categories").handler(this::getProjectCategories);

        this.post("/projects").handler(this::postProjects);
        this.post("/projects/:projectSlug/files").handler(BodyHandler.create(System.getenv("fileUploadDir"))).handler(this::postProjectFiles);
    }

    public void getMods(RoutingContext event) {

        if (this.projectTypeId != null) {
            ResponseUtilities.asSuccessResponse(event, ProjectTypeUtilities.getProjectTypeById(this.conn, projectTypeId));
        } else {
            ResponseUtilities.asErrorResponse(event, ErrorMessages.INTERNAL_SERVER_ERROR);
        }
    }

    public void getProjectsByMods(RoutingContext event) {
        if (this.projectTypeId != null) {

            HttpServerRequest req = event.request();
            DSLContext transaction = DSL.using(conn, SQLDialect.MYSQL);

            String inputPerPage = req.getParam("perPage");

            Integer dbProjectCount = transaction.select(DSL.count())
                    .from(PROJECT)
                    .where(PROJECT.PROJECT_TYPE_ID.eq(this.projectTypeId))
                    .fetchOne(0, int.class);

            String inputPage = req.getParam("page");
            String inputOrder = req.getParam("order");
            String inputOrderBy = req.getParam("orderBy");

            Page page = PagesUtilities.getPageDetails(inputPage, inputPerPage, dbProjectCount);

            TableField<ProjectRecord, ?> tableField = PROJECT.CREATED_AT;

            if (inputOrderBy.equals("name"))
                tableField = PROJECT.NAME;

            List<Long> dbProject = transaction.select(PROJECT.ID)
                    .from(PROJECT)
                    .where(PROJECT.PROJECT_TYPE_ID.eq(this.projectTypeId))
                    .orderBy(inputOrder.equals("asc") ? tableField.asc() : tableField.desc())
                    .limit(page.getOffset(), page.getPerPage())
                    .fetch(0, long.class);

            List<Map<String, Object>> gameListOut = new ArrayList<>();
            for (long projectId : dbProject) {
                gameListOut.add(GameUtilities.getGameById(this.conn, projectId));
            }

            Map<String, Object> outputData = new HashMap<>();
            outputData.put("data", gameListOut);
            outputData.put("page", page);
            outputData.put("order", "desc");
            outputData.put("orderBy", "newest");
            outputData.put("perPage", page.getPerPage());
            outputData.put("totalPageCount", page.getTotalPageCount());

            ResponseUtilities.asSuccessResponse(event, outputData);
        } else {
            ResponseUtilities.asErrorResponse(event, ErrorMessages.INTERNAL_SERVER_ERROR);
        }
    }

    public void postProjects(RoutingContext event) {
        HttpServerRequest req = event.request();

        String token = AuthorizationUtilities.getAuthorizationToken(event);
        if (token != null) {
            if (JWT.isTokenValid(this.conn, token)) {
                JWT jwt = new JWT(token);
                if (!jwt.isExpired()) {
                    Long userId = jwt.getData().getLong("userId");

                    String name = req.getFormAttribute("name");
                    String description = req.getFormAttribute("description");
                    String shortDescription = req.getFormAttribute("shortDescription");
                    String logo = req.getFormAttribute("logo");

                    List<ErrorMessages> errorMessage = new ArrayList<>();

                    if (name == null)
                        errorMessage.add(ErrorMessages.PROJECT_NAME_NULL);
                    if (description == null)
                        errorMessage.add(ErrorMessages.PROJECT_DESCRIPTION_NULL);
                    if (shortDescription == null)
                        errorMessage.add(ErrorMessages.PROJECT_SHORT_DESCRIPTION_NULL);

                    //TODO look into logo
                    if (errorMessage.size() == 0) {
                        String slug = new Slugify().slugify(name);
                        if (ProjectUtilities.getProjectIdBySlug(this.conn, slug, this.projectTypeId) == null) {
                            if (this.projectTypeId != null) {
                                ProjectUtilities.insertProject(conn, name, description, shortDescription, slug, logo, this.projectTypeId, userId);
                            } else {
                                ResponseUtilities.asErrorResponse(event, ErrorMessages.INTERNAL_SERVER_ERROR);
                            }
                        } else {
                            ResponseUtilities.asErrorResponse(event, ErrorMessages.PROJECT_NAME_TAKEN);
                        }
                    } else {
                        ResponseUtilities.asErrorResponse(event, Errors.NOT_FOUND, errorMessage);
                    }
                }
            }
        }
    }

    public void getProjectBySlug(RoutingContext event) {
        String projectSlug = event.request().getParam("projectSlug");

        if (projectSlug != null) {
            Long projectId = ProjectUtilities.getProjectIdBySlug(this.conn, projectSlug, this.projectTypeId);
            if (projectId != null) {
                String token = AuthorizationUtilities.getAuthorizationToken(event);
                Long userId = null;
                if (token != null) {
                    if (JWT.isTokenValid(this.conn, token)) {
                        JWT jwt = new JWT(token);
                        if (jwt.isExpired()) {
                            userId = jwt.getData().getLong("userId");
                        }
                    }
                }
                Map<String, Object> projectObj = ProjectUtilities.getProjectById(this.conn, projectId, userId);
                if (!projectObj.isEmpty()) {
                    ResponseUtilities.asSuccessResponse(event, projectObj);
                } else {
                    ResponseUtilities.asErrorResponse(event, ErrorMessages.PROJECT_NOT_FOUND);
                }
            } else {
                ResponseUtilities.asErrorResponse(event, ErrorMessages.PROJECT_NOT_FOUND);
            }
        }
    }

    public void getProjectMembersByProjectSlug(RoutingContext event) {
        String projectSlug = event.request().getParam("projectSlug");

        if (projectSlug != null) {
            Long projectId = ProjectUtilities.getProjectIdBySlug(this.conn, projectSlug, this.projectTypeId);
            if (projectId != null) {
                List<Map<String, Object>> userListOut = ProjectUtilities.getProjectMembersByProjectId(this.conn, projectId);
                if (!userListOut.isEmpty())
                    ResponseUtilities.asSuccessResponse(event, userListOut);
                else
                    ResponseUtilities.asErrorResponse(event, ErrorMessages.INTERNAL_SERVER_ERROR);
            } else {
                ResponseUtilities.asErrorResponse(event, ErrorMessages.PROJECT_NOT_FOUND);
            }
        } else {
            ResponseUtilities.asErrorResponse(event, ErrorMessages.PROJECT_SLUG_NULL);
        }
    }

    public void getProjectFiles(RoutingContext event) {
        String projectSlug = event.request().getParam("projectSlug");

        if (projectSlug != null) {
            Long projectId = ProjectUtilities.getProjectIdBySlug(this.conn, projectSlug, this.projectTypeId);
            if (projectId != null) {
                ResponseUtilities.asSuccessResponse(event, ProjectUtilities.getProjectFilesById(this.conn, projectId));
            } else {
                ResponseUtilities.asErrorResponse(event, ErrorMessages.PROJECT_NOT_FOUND);
            }
        } else {
            ResponseUtilities.asErrorResponse(event, ErrorMessages.PROJECT_SLUG_NULL);
        }
    }

    public void postProjectFiles(RoutingContext event) {
        String projectSlug = event.request().getParam("projectSlug");

        if (projectSlug != null) {
            HttpServerRequest req = event.request();

            String token = AuthorizationUtilities.getAuthorizationToken(event);
            if (token != null) {
                if (JWT.isTokenValid(this.conn, token)) {
                    JWT jwt = new JWT(token);
                    if (!jwt.isExpired()) {
                        JsonObject payload = jwt.getData();
                        Long userId = payload.getLong("userId");

                        if (event.fileUploads().size() == 1) {
                            FileUpload file = event.fileUploads().iterator().next();
                            long size = file.size();
                            String fileName = file.fileName();

                            String displayName = req.getFormAttribute("displayName");
                            String releaseType = req.getFormAttribute("releaseType");

                            Long parentId = null;
                            if (req.getFormAttribute("parentId") != null)
                                parentId = Long.parseLong(req.getFormAttribute("parentId"));

                            if (displayName == null)
                                displayName = fileName;

                            if (releaseType == null)
                                releaseType = "alpha";

                            ProjectUtilities.insertProjectFiles(this.conn, fileName, displayName, size, releaseType, parentId, projectSlug, userId);
//                            if (id != null) {
                            //TODO Insert into project processing
//                            } else {
                            //TODO Failed to insert
//                            }
                        }
                    }
                }
            }
        }
    }

    public void getProjectCategories(RoutingContext event) {
        String projectSlug = event.request().getParam("projectSlug");

        if (projectSlug != null) {
            Long projectId = ProjectUtilities.getProjectIdBySlug(this.conn, projectSlug, this.projectTypeId);
            if (projectId != null) {
                ResponseUtilities.asSuccessResponse(event, ProjectUtilities.getProjectCategoriesById(this.conn, projectId));
            } else {
                ResponseUtilities.asErrorResponse(event, ErrorMessages.PROJECT_CATEGORY_NOT_FOUND);
            }
        } else {
            ResponseUtilities.asErrorResponse(event, ErrorMessages.PROJECT_SLUG_NULL);
        }
    }
}
