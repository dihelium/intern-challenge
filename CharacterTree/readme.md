## Instructions for executing 1.b) of Intern Challenge. This code serializes a Character Tree, formed by an array of strings with the create function, and deserializes it back to a tree, and prints the strings in the original file with the load function 
* The create function takes an input of array of strings in csv format, and outputs a binary file at location specified in cmd line. The load function loads any binary file created by the create function and prints the strings in the original csv
* After downloading these files, create a csv file of your own, containing an array of strings. Then, on terminal, paste:

* java charTree create /path/to/csv/file/csv-file.csv /path/where/output/is/required/serialized-output.bin
* change /path/to/csv/file/ to actual path to csv file, and csv-file.csv to (the name of your csv).csv
* change /path/where/output/is/required/ to location where binary file should be created

* java charTree load /path/to/binary/output/serialized-ouput.bin
