<%@page import = "ManageJobs"%> 
<%@page import = "Job"%> 


<%

String id = request.getParameter("Id");
String job_Duration = request.getParameter("Job_Duration");
String team_Leader = request.getParameter("Team_Leader");
String job_Duration = request.getParameter("Job_Duration");
Integer reference_Number = request.getParameter("Reference_Number");

Job update = new Job(id, job_Duration, team_Leader, job_Description, reference_Number);

int status = DatabaseDAO.updateJob(update);
if (status == JobStatus)
{
    response.sendRedirect("ManageJobs.jsp");
}
else
{
    system.out.println("Error updating")
}
%>