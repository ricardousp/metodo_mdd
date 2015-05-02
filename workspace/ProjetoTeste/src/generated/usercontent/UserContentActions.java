package generated.usercontent;


import java.util.List;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserContentActions {
	
	public final static String MODERATION_PAGE = "admin/moderation.jsp";
	
	
	public String uploadUserContent(HttpServletRequest req,
			HttpServletResponse resp) {
		String entityName = req.getParameter("entityName");
		String entityId = req.getParameter("entityId");
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String title = req.getParameter("title");
		String content = req.getParameter("content");

        UserContentDAO dao = DefaultUserContentDAOFactoryProvider.getDefaultDAOFactoryInstance().getUserContentDAO();
        dao.insertUserContent(entityName, Long.parseLong(entityId), title, content, name, email);

		try {
			PrintWriter pw = resp.getWriter();
			pw.println("<html>" + "<body>" + "<script>window.close();</script>"
					+ "</body>" + "</html>");
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        return null;
	}
	
	
    public String showModerationForm(HttpServletRequest req, HttpServletResponse resp) {
    	UserContentDAO dao = DefaultUserContentDAOFactoryProvider.getDefaultDAOFactoryInstance().getUserContentDAO();
        
        List<UserContent> unapprovedUserContent = dao.getUnapprovedUserContent();
        
        req.setAttribute("unnaprovedUserContent", unapprovedUserContent);
        
        return MODERATION_PAGE;
    }
    
    public String approveUserContent(HttpServletRequest req, HttpServletResponse resp) {
    	UserContentDAO dao = DefaultUserContentDAOFactoryProvider.getDefaultDAOFactoryInstance().getUserContentDAO();
        
    	String userContentId = req.getParameter("userContentId");
        dao.approveUserContent(Long.parseLong(userContentId));
        
        return showModerationForm(req,resp);
    }
    
    public String deleteUserContent(HttpServletRequest req, HttpServletResponse resp) {
    	UserContentDAO dao = DefaultUserContentDAOFactoryProvider.getDefaultDAOFactoryInstance().getUserContentDAO();
        
    	String userContentId = req.getParameter("userContentId");
        dao.deleteUserContent(Long.parseLong(userContentId));
        
        return showModerationForm(req,resp);
    }    
    	
}
