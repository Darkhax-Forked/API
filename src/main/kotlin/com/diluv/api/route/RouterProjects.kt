package com.diluv.api.route


import com.diluv.api.models.Tables.PROJECT
import com.diluv.api.utils.*
import io.vertx.core.Handler
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.sql.Connection

class RouterProjects(val conn: Connection) {

    fun createRouter(router: Router) {
        //Todo replace with search?? if so, needed to be sort by game version and/or other options
        router.get("/projects").handler(getProjects)
        router.get("/projects/:id").handler(getProjectById)
        router.get("/projects/:id/members").handler(getProjectMembersByProjectId)
//        router.get("/projects/:id/files").handler(getProjectFiles)
        router.get("/projects/:id/categories").handler(getProjectCategories)
    }

    val getProjects = Handler<RoutingContext> { event ->
        val transaction = DSL.using(conn, SQLDialect.MYSQL)

        val dbProject = transaction.select(PROJECT.ID)
                .from(PROJECT)
                .fetch()

        val gameListOut = dbProject.map {
            getProjectById(conn, it.get(PROJECT.ID))
        }

        event.asSuccessResponse(gameListOut)
    }

    val getProjectById = Handler<RoutingContext> { event ->
        val id = event.request().getParam("id").toLongOrNull()

        if (id != null) {
            val projectObj = getProjectById(conn, id)
            if (!projectObj.isEmpty())
                event.asSuccessResponse(projectObj)
            else
                event.asErrorResponse(404, "Project not found")

        }
    }

    val getProjectMembersByProjectId = Handler<RoutingContext> { event ->
        val id = event.request().getParam("id").toLongOrNull()

        if (id != null) {
            val userListOut = getProjectMembersByProjectId(conn, id)
            if (!userListOut.isEmpty())
                event.asSuccessResponse(userListOut)
            else
                event.asErrorResponse(404, "Project not found")

        } else {
            event.asErrorResponse(401, "An id is needed")
        }
    }

//    val getProjectFiles = Handler<RoutingContext> { event ->
//        val id = event.request().getParam("id")
//
//        val query = "SELECT sha256, fileName, DisplayName, releaseType, downloads, size, changelog, createdAt from projectFile where projectId=?"
//        this.databaseConnection.queryWithParams(query, JsonArray().add(id), { res ->
//            if (res.succeeded()) {
//                val resultSet = res.result()
//                val results = resultSet.rows
//                ResponseUtilities.asJSONResponse(event, 200, null, results)
//            } else {
//                ResponseUtilities.asJSONResponse(event, 404, "Project files were not found", null)
//            }
//        })
//    }

    val getProjectCategories = Handler<RoutingContext> { event ->
        val id = event.request().getParam("id").toLongOrNull()

        if (id != null) {
            val projectCategoriesOut = getProjectCategoriesById(conn, id)
            event.asSuccessResponse(projectCategoriesOut)
        } else {
            event.asErrorResponse(401, "An id is needed")
        }
    }
}
