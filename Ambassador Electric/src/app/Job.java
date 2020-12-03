public class Job {
    private String id;
    private String job_Duration;
    private String team_Leader;
    private String job_Description;
    
    private int reference_Number;
	
    Job()
    {
    }
    Job(int reference_number)
    {
        this.reference_Number = reference_number;
    }
    Job(String id, String job_duration,String team_leader, String job_description)
    {
        this.id = id;
        this.job_Duration = job_duration;
        this.team_Leader = team_leader;
        this.job_Description = job_description;
        
    }
     
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    
    public String getJob_Duration() {
        return job_Duration;
    }
    public void setJob_Duration(String job_Duration) {
        this.job_Duration = job_Duration;
    }
    
    
    public String getTeam_Leader() {
        return team_Leader;
    }   
    public void setTeam_Leader(String team_Leader) {
        this.team_Leader = team_Leader;
    }
    
    
    public String getJob_Description() {
        return job_Description;
    }
    public void setJob_Description(String job_Description) {
        this.job_Description = job_Description;
    }
    
    
    public int getReference_Number() {
        return reference_Number;
    }
    public void setId(int reference_Number) {
        this.reference_Number = reference_Number;
    }
 
}
    
    