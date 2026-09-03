# Abend-AID_API
Abend-AID Jenkins plugin to retrieve API request. 
Abend-AID Jenkins Plugin
Abend-AID Jenkins Plugins is used to request Abend-AID APIs(link to doc) that are connected through the CES API configurations.

The Plugin can be downloaded and installed(still figuring this part out).

The Plugin will need:
 A Configuration that will connect to a CES instant that will have a host connection to an Abend-AID Viewer(Defining host connections - BMC Documentation).
 

A Personal Access Token to request the API from CES(Defining security settings - BMC Documentation).


The following APIs are configured for the Jenkins Plugin.

Diagnostic_Summary

Requirements:

Configuration for a CES instance

Token set up in the CES security settings

Select Diagnostic Summary

Report(Entry) number associated with the report that will be used
 <img width="913" height="329" alt="image" src="https://github.com/user-attachments/assets/0a86f68d-cddd-4422-b762-e8511958dced" />

Query(Directory)

Configuration for a CES instance

Token set up in the CES security settings

Select Directory
<img width="912" height="259" alt="image" src="https://github.com/user-attachments/assets/bd9e0b1d-7eb8-4836-b1c8-1ecc8b820be9" />

 
When the Plugin is processed the JSON output will be placed in a formatted JSON file. This file will be in the workspace/buildname/Abend_AID_API.

If the Abend_AID_API folder does not exist it will create the folder.

Each JSON API file will be named after the build name from the Environment variables (BUILD_ID).

The format of the file name will be:

 APIBUILD_ID.txt
 
This will allow other Jenkins build steps or Pipelines to use the JSON data.



WARNING:
The file will be persistent and will not be deleted when it is used. When the process is done with the build it will need to be deleted if the file is not needed after the pipeline process.

