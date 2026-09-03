package io.jenkins.plugins;
import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;
import hudson.util.ListBoxModel;

public class AbendAIDBuilder extends Builder implements SimpleBuildStep {

    private final String name;
    private final String token;
    private final String abendAPI;
    private final String reportNum;        

    @DataBoundConstructor
    public AbendAIDBuilder(String name, String token, String abendAPI, String reportNum) {
        this.name = name;
        this.token = token;
        this.abendAPI = abendAPI;
        this.reportNum = reportNum;
    }

    public String getName() {
        return name;
    }
    public String getToken() {
        return token;
    }
        public String getAPI() {
        return abendAPI;
    }
        public String getReport() {
        return reportNum;
    }


    @DataBoundSetter
    @Override
    public void perform(Run<?, ?> run, FilePath workspace, EnvVars env, Launcher launcher, TaskListener listener)
            throws InterruptedException, IOException {
                        HttpClient client = HttpClient.newHttpClient();



        String URIabend = "test";
            listener.getLogger().println("Token: " + token);
            listener.getLogger().println("API: " + abendAPI);
            listener.getLogger().println("report: " + reportNum);
        if (abendAPI.equals("query")){
            URIabend = String.format("http://%s/compuware/ws/abendaidapi/%s", name, abendAPI);}
        if (abendAPI.equals("report")){
            URIabend = String.format("http://%s/compuware/ws/abendaidapi/diagnosticsummary?data=RPT=%s", name, reportNum);}

        listener.getLogger().println("URIabend: " + URIabend);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URIabend))
                .GET() // Default method, optional to explicitly chain
                .header("Accept", "application/json")
                .header("Authorization", token)
                .build();

        try {

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            listener.getLogger().println("Token: " + token);
            listener.getLogger().println("API: " + abendAPI);
            listener.getLogger().println("report: " + reportNum);
            listener.getLogger().println("Token: " + URIabend);
            listener.getLogger().println("Response" + response.body());
            String responseBody = response.body();
            try {
            int buildnumber = run.getNumber();
            FilePath targetFile = workspace.child("Abend_AID_API/API"+buildnumber+".txt");

            targetFile.write(responseBody, "UTF-8");



        } catch (IOException e) {
            listener.error("Failed to write file due to I/O error: " + e.getMessage());
        } catch (InterruptedException e) {
            listener.error("File writing execution was interrupted: " + e.getMessage());
            Thread.currentThread().interrupt(); // Restore interrupted status
        }

        } catch (IOException | InterruptedException e) {

        }
      
            listener.getLogger().println("connection, " + name);
    
       
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
        public ListBoxModel doFillAbendAPIItems() {
            ListBoxModel items = new ListBoxModel();
            

            items.add("Select API Request", ""); 
            items.add("Directory", "query");
            items.add("Diagnostic Summary", "report");
            
            return items;
        } 

        public FormValidation doCheckName(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.errors_missingName());

            return FormValidation.ok();
        }
        public FormValidation doCheckToken(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.errors_missingToken());

            return FormValidation.ok();
        }
        public FormValidation doCheckReportNum(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.errors_missingReport());

            return FormValidation.ok();
        }
        public FormValidation doCheckAbendAPI(@QueryParameter String value)
                throws IOException, ServletException { 
            if (value == null || value.trim().isEmpty()) {
                return FormValidation.error(Messages.errors_missingRequest());
            }
            

            return FormValidation.ok();
        }
        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

    }


}
