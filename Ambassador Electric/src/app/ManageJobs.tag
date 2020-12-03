<%@page import = "DatabaseDAO"%> 
<%@page import = "ManageJobs"%> 


<%

String newJobs = request.getParameter("Job");
String user = request.getParameter("User");


ManageJobs update = new ManageJobs(newJobs, user);

int status = DatabaseDAO.updateManageJobs(update);
if (status == JobStatus)
{
    response.sendRedirect("ManageJobs.jsp");
}
else
{
    system.out.println("Error updating")
}
%>