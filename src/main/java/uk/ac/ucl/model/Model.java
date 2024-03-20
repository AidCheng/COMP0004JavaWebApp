package uk.ac.ucl.model;

import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;

public class Model {
    private static final String TARGET_FILE_PATH = "data/tempData.csv";
    private static final String[] OPTIONAL_COLUMNS_TO_FILTER = {"BIRTHPLACE","CITY","ETHNICITY","GENDER","RACE","STATE","MARITAL"};
    private static final String FILE_PATH_PREFIX = "user/";
    private static final String FILE_PATH_SUFFIX = ".json";
    static final String JSON_PREFIX_BLOCK =
            """
            {
                "patients":[
            """;
    static final String JSON_SUFFIX_BLOCK =
            """
                ]
            }
            """;
    private DataFrame newFrame = new DataFrame();


    public void readFile(){
        DataLoader newLoader = new DataLoader(TARGET_FILE_PATH);
        newFrame = newLoader.getLoadedData();
    }

    public List<String> getPatientList() {
        return newFrame.getPatientNames();
    }

    public HashMap<Integer, String> getPatientList(List<Integer> patients){
        return newFrame.getPatientNames(patients);
    }

    public HashMap<Integer, String> searchFor(String column, String keyword){
        return newFrame.searchKeyword(column, keyword);
    }

    public HashMap<String, String> getPatientInfo(String id) {
        return newFrame.getPersonalInfo(id);
    }

    public List<String> getColumnNames(){
        return newFrame.getColumnNames();
    }

    public List<Integer> getFilteredPatient(String column, String value) {
        if (column.isEmpty() || value.isEmpty()){
            // filter N/A, return all the patients
            return newFrame.getAllPatientsIndices();
        } else {
            return newFrame.getFilteredPatients(column, value);
        }
    }

    /**
     * Get the available columns and values to filter
     * @return a HashMap containing available columns to filter and available values for this option {Column: [Values]}
     */
    public HashMap<String, List<String>> getAvailFilterOptions(){
        HashMap<String, List<String>> availOptionCollection = new HashMap<>();
        for (String columnName: OPTIONAL_COLUMNS_TO_FILTER){
            List<String> correspondingUniqueValues = newFrame.getUniqueValueOfColumn(columnName);
            availOptionCollection.put(columnName,correspondingUniqueValues);
        }
        return availOptionCollection;
    }

    public List<Integer> findHighestAge(List<Integer> filteredPatients){
       return newFrame.getHighestAge(filteredPatients);
    }

    public List<Integer> findLowestAge(List<Integer> filteredPatients){
        return newFrame.getLowestAge(filteredPatients);
    }

    public List<Integer> sortBy(List<Integer> filteredPatients, String sortMethod, String sortColumn){
        return newFrame.getSortedColumnBy(filteredPatients, sortColumn, sortMethod);
    }

    public void deletePatient(int id) {
       newFrame.removeRow(id);
       writeNewFrame();
    }

    public void editPatient(int id, String column, String newValue) {
        newFrame.putValue(column, id, newValue);
        writeNewFrame();
    }

    private void writeNewFrame(){
        DataWriter.write(newFrame);
    }

    public void addPatient(HashMap<String, String> infoOfPatient) {
        newFrame.addRow(infoOfPatient);
        writeNewFrame();
    }

    public void saveAsJson(String newFilePath) {
        List<String> patients = newFrame.getPatientsInJsonFormat();
        newFilePath = FILE_PATH_PREFIX + newFilePath + FILE_PATH_SUFFIX;
        try(FileWriter fileWriter = new FileWriter(newFilePath)){
            fileWriter.write(JSON_PREFIX_BLOCK);
            for(String patient: patients){
                fileWriter.write(patient);
            }
            fileWriter.write(JSON_SUFFIX_BLOCK);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public HashMap<String, Integer> getAgeDistribution() {
        return newFrame.getAgeDistribution();
    }

    public List<String> getAgeGroups() {
        return newFrame.getAgeGroups();
    }

    public void deletePatientAndUpdateModel(int id){
        deletePatient(id);
        readFile();
    }

    public void addPatientAndUpdateModel(HashMap<String, String> patientInfo){
        checkInput(patientInfo);
        addPatient(patientInfo);
        readFile();
    }

    private void checkInput(HashMap<String, String> patientInfo){
        checkName(patientInfo);
        checkDateInput(patientInfo.get("BIRTHDATE"));
        checkDateInput(patientInfo.get("DEATHDATE"));
    }

    private void checkName(HashMap<String, String> patientInfo) {
        if (patientInfo.get("FIRST").isEmpty() && patientInfo.get("LAST").isEmpty()){
            throw new InvalidParameterException();
        }
    }

    private void checkDateInput(String dateString) throws DateTimeParseException {
        if (!dateString.isEmpty()) {
            // check date input
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(dateString, formatter);
        }
    }


    public void editPatientAndUpdateModel(int id, String column, String newValue){
        if(column.equals("BIRTHDATE") || column.equals("DEATHDATE")){
            checkDateInput(newValue);
        }
        editPatient(id, column, newValue);
        readFile();
    }

}

