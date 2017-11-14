package com.diluv.api.route.minecraft.projects;

import com.diluv.api.DiluvAPI;
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
import org.jooq.TableField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.diluv.api.models.Tables.PROJECT;

public class RouterMods extends RouterImpl {
    private final ProjectType mods;

    public RouterMods(Game minecraft, Vertx vertx) {
        super(vertx);
        this.mods = new ProjectType(minecraft, "mods");
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
        ResponseUtilities.asSuccessResponse(event, this.mods.getData());
    }

    public void getProjectsByMods(RoutingContext event) {
        HttpServerRequest req = event.request();

        String inputPerPage = req.getParam("perPage");

        Integer dbProjectCount = this.mods.getProjectCount();

        String inputPage = req.getParam("page");
        String inputOrder = req.getParam("order");
        String inputOrderBy = req.getParam("orderBy");

        Page page = PagesUtilities.getPageDetails(inputPage, inputPerPage, dbProjectCount);

        TableField<ProjectRecord, ?> tableField = PROJECT.CREATED_AT;

        if (inputOrder == null)
            inputOrder = "asc";

        if (inputOrderBy == null)
            inputOrderBy = "name";

        if (inputOrderBy.equals("name"))
            tableField = PROJECT.NAME;

        List<Long> dbProject = DiluvAPI.getDSLContext().select(PROJECT.ID)
                .from(PROJECT)
                .where(PROJECT.PROJECT_TYPE_ID.eq(this.mods.getId()))
                .orderBy(inputOrder.equals("asc") ? tableField.asc() : tableField.desc())
                .limit(page.getOffset(), page.getPerPage())
                .fetch(0, long.class);

        List<Map<String, Object>> gameListOut = new ArrayList<>();
        for (long projectId : dbProject) {
            gameListOut.add(new Project(this.mods, projectId).getData(null));
        }

        Map<String, Object> outputData = new HashMap<>();
        outputData.put("data", gameListOut);
        outputData.put("page", page);
        outputData.put("order", "desc");
        outputData.put("orderBy", "newest");
        outputData.put("perPage", page.getPerPage());
        outputData.put("totalPageCount", page.getTotalPageCount());

        ResponseUtilities.asSuccessResponse(event, outputData);
    }

    public void postProjects(RoutingContext event) {
        HttpServerRequest req = event.request();

        String token = AuthorizationUtilities.getAuthorizationToken(event);
        if (token != null) {
            if (JWT.isTokenValid(token)) {
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
                        if (!this.mods.doesProjectExist(slug)) {
                            this.mods.insertProject(name, description, shortDescription, slug, logo, userId);
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
            if (this.mods.doesProjectExist(projectSlug)) {
                String token = AuthorizationUtilities.getAuthorizationToken(event);
                Long userId = null;
                if (token != null) {
                    if (JWT.isTokenValid(token)) {
                        JWT jwt = new JWT(token);
                        if (jwt.isExpired()) {
                            userId = jwt.getData().getLong("userId");
                        }
                    }
                }
                Map<String, Object> projectObj = new Project(this.mods, projectSlug).getData(userId);
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
            if (this.mods.doesProjectExist(projectSlug)) {
                List<Map<String, Object>> userListOut = new Project(this.mods, projectSlug).getProjectMembers();
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
            if (this.mods.doesProjectExist(projectSlug)) {
                ResponseUtilities.asSuccessResponse(event, new Project(this.mods, projectSlug).getProjectFiles());
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
                if (JWT.isTokenValid(token)) {
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

                            this.mods.insertProjectFiles(fileName, displayName, size, releaseType, parentId, projectSlug, userId);
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
            if (this.mods.doesProjectExist(projectSlug)) {
                ResponseUtilities.asSuccessResponse(event, new Project(this.mods, projectSlug).getProjectCategories());
            } else {
                ResponseUtilities.asErrorResponse(event, ErrorMessages.PROJECT_CATEGORY_NOT_FOUND);
            }
        } else {
            ResponseUtilities.asErrorResponse(event, ErrorMessages.PROJECT_SLUG_NULL);
        }
    }
}
