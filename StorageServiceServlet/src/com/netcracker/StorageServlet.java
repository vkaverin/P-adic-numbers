package com.netcracker;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.netcracker.storage.DatabaseStorage;

@WebServlet("/StorageServlet")
public class StorageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Storage storage;
	private final static Logger log = Logger.getLogger("Log2File");
	   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StorageServlet() {
        super();
        storage =  new DatabaseStorage();   
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter printWriter = response.getWriter();
		Query query = null;
		
		try {
			log.info("Got new query");
			query = QueryHandler.parseQuery(request, response);
		} catch (Exception e) {
			QueryHandler.errorAlert(printWriter, e.getMessage());
			return;
		}
		
		switch(query.action) {
			case add: {
				if (storage.addEntry(query.entry)) {
					QueryHandler.newEntryAlert(printWriter);
				} else {
					QueryHandler.errorAlert(printWriter, "Couldn't add new entry.");
				}
				break;
			}
			case find: {
				QueryHandler.printQuery(storage, printWriter, query.entry);
				break;
			}
			default: {
				QueryHandler.printAllData(storage, printWriter);
				break;
			}				
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("action", "add");
		doGet(request, response);
	}
}
