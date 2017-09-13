package com.diluv.api.route;

import com.diluv.api.error.Errors;
import com.diluv.api.jwt.JWT;
import com.diluv.api.utils.AuthorizationUtilities;
import com.diluv.api.utils.ProjectTypeUtilities;
import com.diluv.api.utils.ProjectUtilities;
import com.diluv.api.utils.ResponseUtilities;
import com.github.slugify.Slugify;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RouterProjects {

    private final Connection conn;

    public RouterProjects(Connection conn) {
        this.conn = conn;
    }

    public void createRouter(Router router) {
        router.route().handler(BodyHandler.create());

        router.get("/projects/:projectSlug").handler(this::getProjectBySlug);
        router.get("/projects/:projectSlug/members").handler(this::getProjectMembersByProjectSlug);
        router.get("/projects/:projectSlug/files").handler(this::getProjectFiles);
        router.get("/projects/:projectSlug/categories").handler(this::getProjectCategories);

        router.post("/projects").handler(this::postProjects);
        router.post("/projects/:projectSlug/files").handler(this::postProjectFiles);
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
                    String projectTypeSlug = req.getFormAttribute("projectType");

                    List<String> errorMessage = new ArrayList<>();

                    if (name == null)
                        errorMessage.add("Project creation requires a project name");
                    if (description == null)
                        errorMessage.add("Project creation requires a description");
                    if (shortDescription == null)
                        errorMessage.add("Project creation requires a short description");
                    if (logo == null)
                        errorMessage.add("Project creation requires a logo");
                    if (projectTypeSlug == null)
                        errorMessage.add("Project creation requires a project type slug");

                    //TODO look into logo
                    if (errorMessage.size() == 0) {
                        String slug = new Slugify().slugify(name);
                        if (ProjectUtilities.getProjectIdBySlug(this.conn, slug) == null) {
                            Long projectTypeId = ProjectTypeUtilities.getProjectTypeIdBySlug(this.conn, projectTypeSlug);
                            if (projectTypeId != null) {
                                ProjectUtilities.insertProject(conn, name, description, shortDescription, slug, logo, projectTypeId, userId);
                            } else {
                                ResponseUtilities.asErrorResponse(event, Errors.BAD_REQUEST, "Project type doesn't exist.");
                            }
                        } else {
                            ResponseUtilities.asErrorResponse(event, Errors.BAD_REQUEST, "Project name is already used, please pick another");
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
            Long projectId = ProjectUtilities.getProjectIdBySlug(this.conn, projectSlug);
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
                    ResponseUtilities.asErrorResponse(event, Errors.NOT_FOUND, "Project not found");
                }
            } else {
                ResponseUtilities.asErrorResponse(event, Errors.UNAUTHORIZED, "Project not found");
            }
        }
    }

    public void getProjectMembersByProjectSlug(RoutingContext event) {
        String projectSlug = event.request().getParam("projectSlug");

        if (projectSlug != null) {
            Long projectId = ProjectUtilities.getProjectIdBySlug(this.conn, projectSlug);
            if (projectId != null) {
                List<Map<String, Object>> userListOut = ProjectUtilities.getProjectMembersByProjectId(this.conn, projectId);
                if (!userListOut.isEmpty())
                    ResponseUtilities.asSuccessResponse(event, userListOut);
                else
                    ResponseUtilities.asErrorResponse(event, Errors.NOT_FOUND, "Project not found");
            } else {
                ResponseUtilities.asErrorResponse(event, Errors.UNAUTHORIZED, "Project not found");
            }
        } else {
            ResponseUtilities.asErrorResponse(event, Errors.UNAUTHORIZED, "An id is needed");
        }
    }

    public void getProjectFiles(RoutingContext event) {
        String projectSlug = event.request().getParam("projectSlug");

        if (projectSlug != null) {
            Long projectId = ProjectUtilities.getProjectIdBySlug(this.conn, projectSlug);
            if (projectId != null) {
                ResponseUtilities.asSuccessResponse(event, ProjectUtilities.getProjectFilesById(this.conn, projectId));
            } else {
                ResponseUtilities.asErrorResponse(event, Errors.UNAUTHORIZED, "Project not found");
            }
        } else {
            ResponseUtilities.asErrorResponse(event, Errors.UNAUTHORIZED, "An id is needed");
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
            Long projectId = ProjectUtilities.getProjectIdBySlug(this.conn, projectSlug);
            if (projectId != null) {
                ResponseUtilities.asSuccessResponse(event, ProjectUtilities.getProjectCategoriesById(this.conn, projectId));
            } else {
                ResponseUtilities.asErrorResponse(event, Errors.UNAUTHORIZED, "Project categories not found");
            }
        } else {
            ResponseUtilities.asErrorResponse(event, Errors.UNAUTHORIZED, "An id is needed");
        }
    }
}
