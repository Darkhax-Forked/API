package com.diluv.api.route

import com.diluv.api.utils.*
import com.diluv.catalejo.Catalejo
import io.vertx.core.Handler
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import java.io.File
import java.sql.Connection

class RouterProjects(val conn: Connection) {

    fun createRouter(router: Router) {
        router.route().handler(BodyHandler.create())

        router.get("/projects/:projectSlug").handler(getProjectBySlug)
        router.get("/projects/:projectSlug/members").handler(getProjectMembersByProjectSlug)
        router.get("/projects/:projectSlug/files").handler(getProjectFiles)
        router.get("/projects/:projectSlug/categories").handler(getProjectCategories)

        router.post("/projects").handler(postProjects)
        router.post("/projects/:projectSlug/files").handler(postProjectFiles)
        router.options("/projects/:projectSlug/files").handler(respond)
    }

    val respond = Handler<RoutingContext> { event ->
        val response = event.response()
        response.statusCode = 200
        response.putHeader("Content-Type", "application/json; charset=utf-8")
        response.putHeader("Access-Control-Allow-Origin", "*")
        response.putHeader("Access-Control-Allow-Methods", "POST")
        response.putHeader("Access-Control-Allow-Headers", "Content-Type, Authorization")
        response.end()
    }

    val postProjects = Handler<RoutingContext> { event ->
        val req = event.request()

        val token = event.getAuthorizationToken(conn, false)
        if (token != null) {
            val userId = token.data.getLong("userId")

            val name = req.getFormAttribute("name")
            val description = req.getFormAttribute("description")
            val shortDescription = req.getFormAttribute("shortDescription")
            val logo = req.getFormAttribute("logo")
//                    val projectTypeId = req.getFormAttribute("projectTypeId").toLongOrNull()

            val errorMessage = arrayListOf<String>()

            if (name == null)
                errorMessage.add("Project creation requires a project name")
            if (description == null)
                errorMessage.add("Project creation requires a description")
            if (shortDescription == null)
                errorMessage.add("Project creation requires a short description")
            if (logo == null)
                errorMessage.add("Project creation requires a logo")
            //TODO
//                    if (projectTypeId == null)
//                        errorMessage.add("Project creation requires a project type id")

            //TODO look into logo
            if (errorMessage.size == 0) {
                //todo generate slug
                val slug = ""
//                        insertProject(conn, name, description, shortDescription, slug, logo, projectTypeId as Long, userId)
            } else {
                event.asErrorResponse(404, errorMessage)
            }
        }
    }

    val getProjectBySlug = Handler<RoutingContext> { event ->
        val projectSlug = event.request().getParam("projectSlug")

        if (projectSlug != null) {
            val projectId = conn.getProjectIdBySlug(projectSlug)
            if (projectId != null) {
                val token = event.getAuthorizationToken(conn, true)
                val projectObj = conn.getProjectById(projectId, token)
                if (!projectObj.isEmpty()) {
                    event.asSuccessResponse(projectObj)
                } else {
                    event.asErrorResponse(404, "Project not found")
                }
            } else {
                event.asErrorResponse(401, "Project not found")
            }
        }
    }

    val getProjectMembersByProjectSlug = Handler<RoutingContext> { event ->
        val projectSlug = event.request().getParam("projectSlug")

        if (projectSlug != null) {
            val projectId = conn.getProjectIdBySlug(projectSlug)
            if (projectId != null) {
                val userListOut = conn.getProjectMembersByProjectId(projectId)
                if (!userListOut.isEmpty())
                    event.asSuccessResponse(userListOut)
                else
                    event.asErrorResponse(404, "Project not found")
            } else {
                event.asErrorResponse(401, "Project not found")
            }
        } else {
            event.asErrorResponse(401, "An id is needed")
        }
    }

    val getProjectFiles = Handler<RoutingContext> { event ->
        val projectSlug = event.request().getParam("projectSlug")

        if (projectSlug != null) {
            val projectId = conn.getProjectIdBySlug(projectSlug)
            if (projectId != null) {
                event.asSuccessResponse(conn.getProjectFilesById(projectId))
            } else {
                event.asErrorResponse(401, "Project not found")
            }
        } else {
            event.asErrorResponse(401, "An id is needed")
        }
    }

    val postProjectFiles = Handler<RoutingContext> { event ->
        val projectSlug = event.request().getParam("projectSlug")

        if (projectSlug != null) {
            val req = event.request()

            val token = event.getAuthorizationToken(conn, true)
            if (token != null) {
                val payload = token.data
                val userId = payload.getLong("userId")

                if (event.fileUploads().size == 1) {
                    val file = event.fileUploads().iterator().next()
                    val size = file.size()
                    val fileName = file.fileName()

                    val map = HashMap<String, Any>()
                    val catalejo = Catalejo()
                    catalejo.add(Catalejo.SHA_256_READER)
                    catalejo.readFileMeta(map, File(file.uploadedFileName()))

                    var displayName = req.getFormAttribute("displayName")
                    var releaseType = req.getFormAttribute("releaseType")

                    var parentId: Long? = null
                    if (req.getFormAttribute("parentId") != null)
                        parentId = req.getFormAttribute("parentId").toLongOrNull()

                    if (displayName == null)
                        displayName = fileName

                    if (releaseType == null)
                        releaseType = "alpha"


                    val sha256 = map["SHA-256"]
                    println(map)
                    if (sha256 != null) {
                        val id = conn.insertProjectFiles(sha256 as String, fileName, displayName, size, releaseType, "Review", parentId, projectSlug, userId)
                        if (id != null) {
                            //TODO Insert into project processing
                        } else {
                            //TODO Failed to insert
                        }
                    }
                }
            }
        }
    }

    val getProjectCategories = Handler<RoutingContext> { event ->
        val projectSlug = event.request().getParam("projectSlug")

        if (projectSlug != null) {
            val projectId = conn.getProjectIdBySlug(projectSlug)
            if (projectId != null) {
                event.asSuccessResponse(conn.getProjectCategoriesById(projectId))
            } else {
                event.asErrorResponse(401, "Project categories not found")
            }
        } else {
            event.asErrorResponse(401, "An id is needed")
        }
    }
}
