package com.example.fx_sms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class DashboardController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    @FXML
    private Button home_btn;

    @FXML
    private Button addTeachers_btn;

    @FXML
    private Button addStudents_btn;

    @FXML
    private Button availableCourse_btn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_totalEnrolled;

    @FXML
    private Label home_totalFemale;

    @FXML
    private Label home_totalMale;

    @FXML
    private BarChart<?, ?> home_totalEnrolledChart;

    @FXML
    private AreaChart<?, ?> home_totalFemaleChart;

    @FXML
    private LineChart<?, ?> home_totalMaleChart;

    @FXML
    private AnchorPane addTeachers_form;

    @FXML
    private TextField addTeachers_search;

    @FXML
    private TableView<Teacher> addTeachers_tableView;

    @FXML
    private TableColumn<studentData, String> addTeachers_col_teacherNum;

    @FXML
    private TableColumn<studentData, String> addTeachers_col_firstName;

    @FXML
    private TableColumn<studentData, String> addTeachers_col_lastName;

    @FXML
    private TableColumn<studentData, String> addTeachers_col_gender;

    @FXML
    private TableColumn<studentData, String> addTeachers_col_birth;

    @FXML
    private TableColumn<studentData, String> addTeachers_col_phone;

    @FXML
    private TableColumn<studentData, String> addTeachers_col_lvl;

    @FXML
    private TextField addTeachers_teacherNum;

    @FXML
    private TextField addTeachers_phone;

    @FXML
    private ComboBox<?> addTeachers_lvl;

    @FXML
    private TextField addTeachers_firstName;

    @FXML
    private TextField addTeachers_lastName;

    @FXML
    private DatePicker addTeachers_birth;

    @FXML
    private ComboBox<String> addTeachers_gender;

    @FXML
    private ImageView addTeachers_imageView;

    @FXML
    private Button addTeachers_insertBtn;

    @FXML
    private Button addTeachers_addBtn;

    @FXML
    private Button addTeachers_updateBtn;

    @FXML
    private Button addTeachers_deleteBtn;

    @FXML
    private Button addTeachers_clearBtn;

    @FXML
    private AnchorPane availableCourse_form;

    @FXML
    private TextField availableCourse_course;

    @FXML
    private TextField availableCourse_description;

    @FXML
    private TextField availableCourse_degree;

    @FXML
    private Button availableCourse_addBtn;

    @FXML
    private Button availableCourse_updateBtn;

    @FXML
    private Button availableCourse_clearBtn;

    @FXML
    private Button availableCourse_deleteBtn;

    @FXML
    private TableView<courseData> availableCourse_tableView;

    @FXML
    private TableColumn<courseData, String> availableCourse_col_course;

    @FXML
    private TableColumn<courseData, String> availableCourse_col_description;

    @FXML
    private TableColumn<courseData, String> availableCourse_col_degree;

    @FXML
    private AnchorPane addStudents_form;

    @FXML
    private TextField addStudents_search;

    @FXML
    private TableView<Student> addStudents_tableView;

    @FXML
    private TableColumn<studentData, String> addStudents_col_studentNum;

    @FXML
    private TableColumn<studentData, String> addStudents_col_firstName;

    @FXML
    private TableColumn<studentData, String> addStudents_col_lastName;

    @FXML
    private TableColumn<studentData, String> addStudents_col_gender;

    @FXML
    private TableColumn<studentData, String> addStudents_col_birth;

    @FXML
    private TableColumn<studentData, String> addStudents_col_phone;

    @FXML
    private TableColumn<studentData, String> addStudents_col_lvl;

    @FXML
    private TableColumn<studentData, String> addStudents_col_course;

    @FXML
    private TextField addStudents_studentNum;

    @FXML
    private TextField addStudents_phone;

    @FXML
    private ComboBox<String> addStudents_lvl;

    @FXML
    private TextField addStudents_firstName;

    @FXML
    private TextField addStudents_lastName;

    @FXML
    private DatePicker addStudents_birth;

    @FXML
    private ComboBox<String> addStudents_gender;

    @FXML
    private ComboBox<String> addStudents_course;

    @FXML
    private ImageView addStudents_imageView;

    @FXML
    private Button addStudents_insertBtn;

    @FXML
    private Button addStudents_addBtn;

    @FXML
    private Button addStudents_updateBtn;

    @FXML
    private Button addStudents_deleteBtn;

    @FXML
    private Button addStudents_clearBtn;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private Image image;

    public void homeDisplayTotalEnrolledStudents() {

        String sql = "SELECT COUNT(stud_id) FROM students";

        try {
            connect = DBManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int countEnrolled = 0;

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countEnrolled = result.getInt("count");
            }

            home_totalEnrolled.setText(String.valueOf(countEnrolled));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeDisplayFemaleEnrolled() {

        String sql = "SELECT COUNT(stud_id) FROM students WHERE gender = 'Female'";

        try {
            connect = DBManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            int countFemale = 0;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countFemale = result.getInt("COUNT");
            }

            home_totalFemale.setText(String.valueOf(countFemale));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeDisplayMaleEnrolled() {

        String sql = "SELECT COUNT(stud_id) FROM students WHERE gender = 'Male'";

        try {
            connect = DBManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            int countMale = 0;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countMale = result.getInt("COUNT");
            }
            home_totalMale.setText(String.valueOf(countMale));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeDisplayTotalEnrolledChart() {

        home_totalEnrolledChart.getData().clear();

        String sql = "SELECT entry_date, COUNT(stud_id) \n" +
                "FROM Students \n" +
                "GROUP BY entry_date \n" +
                "ORDER BY entry_date ASC \n" +
                "LIMIT 5;";

        try {
            connect = DBManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            home_totalEnrolledChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeDisplayFemaleEnrolledChart() {

        home_totalFemaleChart.getData().clear();

        String sql = "SELECT entry_date, COUNT(stud_id) FROM students WHERE gender = 'Female' GROUP BY entry_date ORDER BY entry_date ASC LIMIT 5";

        try {
            connect = DBManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            home_totalFemaleChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void homeDisplayEnrolledMaleChart() {

        home_totalMaleChart.getData().clear();

        String sql = "SELECT entry_date, COUNT(stud_id) FROM students WHERE gender = 'Male' GROUP BY entry_date ORDER BY entry_date ASC LIMIT 5";

        try {
            connect = DBManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            home_totalMaleChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Teacher CRUD Functions
    public void addTeachersAdd() {

        String insertData = "INSERT INTO Teachers "
                + "(teacher_id, first_name, last_name, gender, birth_date, phone_num, english_lvl, image) "
                + "VALUES(?,?,?,?,?,?,?,?)";

        try {
            Alert alert;

            if (addTeachers_teacherNum.getText().isEmpty()
                    || addTeachers_firstName.getText().isEmpty()
                    || addTeachers_lastName.getText().isEmpty()
                    || addTeachers_gender.getSelectionModel().getSelectedItem() == null
                    || addTeachers_birth.getValue() == null
                    || addTeachers_lvl.getSelectionModel().getSelectedItem() == null
                    || addTeachers_phone.getText().isEmpty()
                    || getData.path == null || getData.path == "") {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                // CHECK IF THE STUDENTNUMBER IS ALREADY EXIST
                String checkData = "SELECT teacher_id FROM Teachers WHERE teacher_id = "
                        + addTeachers_teacherNum.getText();


                try (Connection con = DBManager.getConnection();
                     PreparedStatement pst = con.prepareStatement(checkData)) {

                    result = pst.executeQuery();

                    if (result.next()) {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Teacher #" + addTeachers_teacherNum.getText() + " was already exist!");
                        alert.showAndWait();
                    } else {
                        try (Connection conn = DBManager.getConnection();
                             PreparedStatement prst = conn.prepareStatement(insertData)) {

                            prst.setInt(1, Integer.parseInt(addTeachers_teacherNum.getText()));
                            prst.setString(2, addTeachers_firstName.getText());
                            prst.setString(3, addTeachers_lastName.getText());
                            prst.setString(4, (String) addTeachers_gender.getSelectionModel().getSelectedItem());
                            /*Date date = new Date();
                            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                            prst.setString(5, String.valueOf(sqlDate));*/
                            prst.setDate(5, Date.valueOf(addTeachers_birth.getValue()));
                            prst.setString(6, addTeachers_phone.getText());
                            prst.setString(7, (String) addTeachers_lvl.getSelectionModel().getSelectedItem());
                            String uri = getData.path;
                            uri = uri.replace("\\", "\\\\");
                            prst.setString(8, uri);

                            prst.executeUpdate();
                            System.out.println("Sucessfully added.");

                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Successfully Added!");
                            alert.showAndWait();

                            // TO UPDATE THE TABLEVIEW
                            addTeachersShowListData();
                            // TO CLEAR THE FIELDS
                            addTeachersClear();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addTeachersUpdate() {
        String uri = getData.path;
        uri = uri.replace("\\", "\\\\");

        String updateData = "UPDATE teachers SET "
                + " first_name = '" + addTeachers_firstName.getText()
                + "', last_name = '" + addTeachers_lastName.getText()
                + "', gender = '" + addTeachers_gender.getSelectionModel().getSelectedItem()
                + "', birth_date = '" + addTeachers_birth.getValue()
                + "', phone_num = '" + addTeachers_phone.getText()
                + "', english_lvl = '" + addTeachers_lvl.getSelectionModel().getSelectedItem()
                + "', image = '" + uri + "' WHERE teacher_id = '"
                + addTeachers_teacherNum.getText() + "'";

        try {
            connect = DBManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Alert alert;
            if (addTeachers_teacherNum.getText().isEmpty()
                    || addTeachers_firstName.getText().isEmpty()
                    || addTeachers_lastName.getText().isEmpty()
                    || addTeachers_gender.getSelectionModel().getSelectedItem() == null
                    || addTeachers_birth.getValue() == null
                    || addTeachers_lvl.getSelectionModel().getSelectedItem() == null
                    || addTeachers_phone.getText().isEmpty()
                    || getData.path == null || getData.path == "") {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Teacher #" + addTeachers_teacherNum.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(updateData);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // TO UPDATE THE TABLEVIEW
                    addTeachersShowListData();
                    // TO CLEAR THE FIELDS
                    addTeachersClear();

                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addTeachersDelete() {
        String deleteData = "DELETE FROM teachers WHERE teacher_id = "
                + addTeachers_teacherNum.getText();

        try {
            connect = DBManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Alert alert;
            if (addTeachers_teacherNum.getText().isEmpty()
                    || addTeachers_firstName.getText().isEmpty()
                    || addTeachers_lastName.getText().isEmpty()
                    || addTeachers_gender.getSelectionModel().getSelectedItem() == null
                    || addTeachers_birth.getValue() == null
                    || addTeachers_lvl.getSelectionModel().getSelectedItem() == null
                    || addTeachers_phone.getText().isEmpty()
                    || getData.path == null || getData.path == "") {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Teacher #" + addTeachers_teacherNum.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    statement = connect.createStatement();
                    statement.executeUpdate(deleteData);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    // TO UPDATE THE TABLEVIEW
                    addTeachersShowListData();
                    // TO CLEAR THE FIELDS
                    addTeachersClear();

                } else {
                    return;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addTeachersClear() {
        addTeachers_teacherNum.setText("");
        addTeachers_firstName.setText("");
        addTeachers_lastName.setText("");
        addTeachers_gender.getSelectionModel().clearSelection();
        addTeachers_birth.setValue(null);
        addTeachers_lvl.getSelectionModel().clearSelection();
        addTeachers_phone.setText("");
        addTeachers_imageView.setImage(null);

        getData.path = "";
    }

    public void addTeachersInsertImage() {

        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {

            image = new Image(file.toURI().toString(), 120, 149, false, true);
            addTeachers_imageView.setImage(image);

            getData.path = file.getAbsolutePath();

        }
    }

    public void addTeachersSearch() {
        FilteredList<Teacher> filter = new FilteredList<>(addTeachersListD, e -> true);

        addTeachers_search.setOnKeyReleased(event -> {
            filter.setPredicate(predicateTeacherData -> {
                String newValue = addTeachers_search.getText();
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateTeacherData.getFirst_name().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacherData.getLast_name().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
            addTeachers_tableView.setItems(filter);
        });

        SortedList<Teacher> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(addTeachers_tableView.comparatorProperty());
        addTeachers_tableView.setItems(sortList);
    }

    private String[] lvlList = {"A1", "A2", "B1", "B2", "C1", "C2"};

    public void addTeachersLvlList() {

        List<String> yearL = new ArrayList<>();

        for (String data : lvlList) {
            yearL.add(data);
        }

        ObservableList ObList = FXCollections.observableArrayList(yearL);
        addTeachers_lvl.setItems(ObList);

    }
    //public void addStudentsCourseList() {}
    private String[] genderList = {"Male", "Female", "Others"};

    public void addTeachersGenderList() {
        List<String> genderL = new ArrayList<>();

        for (String data : genderList) {
            genderL.add(data);
        }

        ObservableList<String> ObList = FXCollections.observableArrayList(genderL);
        addTeachers_gender.setItems(ObList);

    }
    public ObservableList<Teacher> addTeachersListData() {
        ObservableList<Teacher> listTeachers = FXCollections.observableArrayList();

        String sql = "SELECT * FROM teachers";

        try {
            connect = DBManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Teacher teacherD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                teacherD = new Teacher(
                        result.getInt("teacher_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("gender"),
                        result.getDate("birth_date"),
                        result.getString("phone_num"),
                        result.getString("english_lvl"),
                        result.getString("image"));

                listTeachers.add(teacherD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listTeachers;
    }

    private ObservableList<Teacher> addTeachersListD;
    public void addTeachersShowListData() {
        addTeachersListD = addTeachersListData();

        addTeachers_col_teacherNum.setCellValueFactory(new PropertyValueFactory<>("teacher_id"));
        addTeachers_col_firstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        addTeachers_col_lastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        addTeachers_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addTeachers_col_birth.setCellValueFactory(new PropertyValueFactory<>("birth_date"));
        addTeachers_col_phone.setCellValueFactory(new PropertyValueFactory<>("phone_num"));
        addTeachers_col_lvl.setCellValueFactory(new PropertyValueFactory<>("english_lvl"));

        addTeachers_tableView.setItems(addTeachersListD);
    }
    public void addTeachersSelect() {
        Teacher teacherD = addTeachers_tableView.getSelectionModel().getSelectedItem();
        int num = addTeachers_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addTeachers_teacherNum.setText(String.valueOf(teacherD.getTeacher_id()));
        addTeachers_firstName.setText(teacherD.getFirst_name());
        addTeachers_lastName.setText(teacherD.getLast_name());
        addTeachers_birth.setValue(LocalDate.parse(String.valueOf(teacherD.getBirth_date())));
        addTeachers_phone.setText(teacherD.getPhone_num());

        String uri = "file:" + teacherD.getImage();

        image = new Image(uri, 120, 149, false, true);
        addTeachers_imageView.setImage(image);

        getData.path = teacherD.getImage();
    }

    //Course CRUD Functions
    public void availableCourseAdd() {
        String insertData = "INSERT INTO courses (course_id,course_name,teacher_id) VALUES(?,?,?)";

        try {
            connect = DBManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Alert alert;

            if (availableCourse_course.getText().isEmpty()
                    || availableCourse_description.getText().isEmpty()
                    || availableCourse_degree.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
//            CHECK IF THE COURSE YOU WANT TO INSERT IS ALREADY EXIST!
                String checkData = "SELECT courses FROM courses WHERE course_id = '"
                        + availableCourse_course.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Course: " + availableCourse_course.getText() + " was already exist!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, availableCourse_course.getText());
                    prepare.setString(2, availableCourse_description.getText());
                    prepare.setInt(3, Integer.parseInt(availableCourse_degree.getText()));

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    // TO BECOME UPDATED OUR TABLEVIEW ONCE THE DATA WE GAVE SUCCESSFUL
                    availableCourseShowListData();
                    // TO CLEAR THE TEXT FIELDS
                    availableCourseClear();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void availableCourseUpdate() {
        String updateData = "UPDATE courses SET course_name = '"
                + availableCourse_description.getText() + "', teacher_id = "
                + availableCourse_degree.getText() + " WHERE course_id = '"
                + availableCourse_course.getText() + "'";

        try {
            connect = DBManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Alert alert;

            if (availableCourse_course.getText().isEmpty()
                    || availableCourse_description.getText().isEmpty()
                    || availableCourse_degree.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Course: " + availableCourse_course.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(updateData);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // TO BECOME UPDATED OUR TABLEVIEW ONCE THE DATA WE GAVE SUCCESSFUL
                    availableCourseShowListData();
                    // TO CLEAR THE TEXT FIELDS
                    availableCourseClear();

                } else {
                    return;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void availableCourseDelete() {
        String deleteData = "DELETE FROM courses WHERE course_id = '"
                + availableCourse_course.getText() + "'";

        try {
            connect = DBManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Alert alert;

            if (availableCourse_course.getText().isEmpty()
                    || availableCourse_description.getText().isEmpty()
                    || availableCourse_degree.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
//                ALL GOOD GUYS! NOW LETS PROCEED TO ADD STUDENTS FORM
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Course: " + availableCourse_course.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(deleteData);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    // TO BECOME UPDATED OUR TABLEVIEW ONCE THE DATA WE GAVE SUCCESSFUL
                    availableCourseShowListData();
                    // TO CLEAR THE TEXT FIELDS
                    availableCourseClear();

                } else {
                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void availableCourseClear() {
        availableCourse_course.setText("");
        availableCourse_description.setText("");
        availableCourse_degree.setText("");
    }

    public ObservableList<courseData> availableCourseListData() {
        ObservableList<courseData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM courses";

        try {
            connect = DBManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            courseData courseD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                courseD = new courseData(result.getString("course_id"),
                        result.getString("course_name"),
                        result.getInt("teacher_id"));

                listData.add(courseD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<courseData> availableCourseList;

    public void availableCourseShowListData() {
        availableCourseList = availableCourseListData();

        availableCourse_col_course.setCellValueFactory(new PropertyValueFactory<>("course"));
        availableCourse_col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        availableCourse_col_degree.setCellValueFactory(new PropertyValueFactory<>("teacher_id"));

        availableCourse_tableView.setItems(availableCourseList);
    }
    public void availableCourseSelect() {
        courseData courseD = availableCourse_tableView.getSelectionModel().getSelectedItem();
        int num = availableCourse_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        availableCourse_course.setText(courseD.getCourse());
        availableCourse_description.setText(courseD.getDescription());
        availableCourse_degree.setText(String.valueOf(courseD.getTeacher_id()));
    }

    //Student CRUD Functions
    public void addStudentsAdd() {

        String insertData = "INSERT INTO students "
                + "(stud_id, first_name, last_name, gender, birth_date, phone_num, english_lvl, course_id, image, entry_date) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?)";

        try {
            Alert alert;

            if (addStudents_studentNum.getText().isEmpty()
                    || addStudents_firstName.getText().isEmpty()
                    || addStudents_lastName.getText().isEmpty()
                    || addStudents_gender.getSelectionModel().getSelectedItem() == null
                    || addStudents_birth.getValue() == null
                    || addStudents_lvl.getSelectionModel().getSelectedItem() == null
                    || addStudents_phone.getText().isEmpty()
                    || addStudents_phone.getText().isEmpty()
                    || addStudents_course.getSelectionModel().getSelectedItem() == null
                    || getData.path == null || getData.path == "") {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                // CHECK IF THE STUDENTNUMBER IS ALREADY EXIST
                String checkData = "SELECT stud_id FROM students WHERE stud_id = "
                        + addStudents_studentNum.getText();


                try (Connection con = DBManager.getConnection();
                     PreparedStatement pst = con.prepareStatement(checkData)) {

                    result = pst.executeQuery();

                    if (result.next()) {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Student #" + addStudents_studentNum.getText() + " was already exist!");
                        alert.showAndWait();
                    } else {
                        try (Connection conn = DBManager.getConnection();
                             PreparedStatement prst = conn.prepareStatement(insertData)) {

                            prst.setInt(1, Integer.parseInt(addStudents_studentNum.getText()));
                            prst.setString(2, addStudents_firstName.getText());
                            prst.setString(3, addStudents_lastName.getText());
                            prst.setString(4, (String) addStudents_gender.getSelectionModel().getSelectedItem());
                            prst.setDate(5, Date.valueOf(addStudents_birth.getValue()));
                            prst.setString(6, addStudents_phone.getText());
                            prst.setString(7, (String) addStudents_lvl.getSelectionModel().getSelectedItem());
                            prst.setString(8, (String) addStudents_course.getSelectionModel().getSelectedItem());
                            String uri = getData.path;
                            uri = uri.replace("\\", "\\\\");
                            prst.setString(9, uri);
                            java.util.Date date = new java.util.Date();
                            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                            prst.setString(10, String.valueOf(sqlDate));

                            prst.executeUpdate();
                            System.out.println("Sucessfully added.");

                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Successfully Added!");
                            alert.showAndWait();

                            // TO UPDATE THE TABLEVIEW
                            addStudentsShowListData();
                            // TO CLEAR THE FIELDS
                            addStudentsClear();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addStudentsUpdate() {
        String uri = getData.path;
        uri = uri.replace("\\", "\\\\");

        String updateData = "UPDATE students SET "
                + " first_name = '" + addStudents_firstName.getText()
                + "', last_name = '" + addStudents_lastName.getText()
                + "', gender = '" + addStudents_gender.getSelectionModel().getSelectedItem()
                + "', birth_date = '" + addStudents_birth.getValue()
                + "', phone_num = '" + addStudents_phone.getText()
                + "', english_lvl = '" + addStudents_lvl.getSelectionModel().getSelectedItem()
                + "', course_id = '" + addStudents_course.getSelectionModel().getSelectedItem()
                + "', image = '" + uri + "' WHERE stud_id = '"
                + addStudents_studentNum.getText() + "'";

        try {
            connect = DBManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Alert alert;
            if (addStudents_studentNum.getText().isEmpty()
                    || addStudents_firstName.getText().isEmpty()
                    || addStudents_lastName.getText().isEmpty()
                    || addStudents_gender.getSelectionModel().getSelectedItem() == null
                    || addStudents_birth.getValue() == null
                    || addStudents_lvl.getSelectionModel().getSelectedItem() == null
                    || addStudents_phone.getText().isEmpty()
                    || addStudents_phone.getText().isEmpty()
                    || addStudents_course.getSelectionModel().getSelectedItem() == null
                    || getData.path == null || getData.path == "") {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Student #" + addStudents_studentNum.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(updateData);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // TO UPDATE THE TABLEVIEW
                    addStudentsShowListData();
                    // TO CLEAR THE FIELDS
                    addStudentsClear();

                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addStudentsDelete() {
       String deleteData = "DELETE FROM students WHERE stud_id = "
                + addStudents_studentNum.getText();

        try {
            connect = DBManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Alert alert;
            if (addStudents_studentNum.getText().isEmpty()
                    || addStudents_firstName.getText().isEmpty()
                    || addStudents_lastName.getText().isEmpty()
                    || addStudents_gender.getSelectionModel().getSelectedItem() == null
                    || addStudents_birth.getValue() == null
                    || addStudents_lvl.getSelectionModel().getSelectedItem() == null
                    || addStudents_phone.getText().isEmpty()
                    || addStudents_phone.getText().isEmpty()
                    || addStudents_course.getSelectionModel().getSelectedItem() == null
                    || getData.path == null || getData.path == "") {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Student #" + addStudents_studentNum.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    statement = connect.createStatement();
                    statement.executeUpdate(deleteData);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    // TO UPDATE THE TABLEVIEW
                    addStudentsShowListData();
                    // TO CLEAR THE FIELDS
                    addStudentsClear();

                } else {
                    return;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addStudentsClear() {
        addStudents_studentNum.setText("");
        addStudents_firstName.setText("");
        addStudents_lastName.setText("");
        addStudents_gender.getSelectionModel().clearSelection();
        addStudents_birth.setValue(null);
        addStudents_lvl.getSelectionModel().clearSelection();
        addStudents_phone.setText("");
        addStudents_course.getSelectionModel().clearSelection();
        addStudents_imageView.setImage(null);

        getData.path = "";
    }

    public void addStudentsInsertImage() {

        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {

            image = new Image(file.toURI().toString(), 120, 149, false, true);
            addStudents_imageView.setImage(image);

            getData.path = file.getAbsolutePath();

        }
    }

    public void addStudentsSearch() {
        FilteredList<Student> filter = new FilteredList<>(addStudentsListD, e -> true);

        addStudents_search.setOnKeyReleased(event -> {
            filter.setPredicate(predicateTeacherData -> {
                String newValue = addStudents_search.getText();
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateTeacherData.getFirst_name().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacherData.getLast_name().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateTeacherData.getGender().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
            addStudents_tableView.setItems(filter);
        });

        SortedList<Student> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(addStudents_tableView.comparatorProperty());
        addStudents_tableView.setItems(sortList);
    }

    public void addStudentsLvlList() {

        List<String> lvlL = new ArrayList<>();

        for (String data : lvlList) {
            lvlL.add(data);
        }

        ObservableList<String> ObList = FXCollections.observableArrayList(lvlL);
        addStudents_lvl.setItems(ObList);

    }

    public void addStudentsGenderList() {
        List<String> genderL = new ArrayList<>();

        for (String data : genderList) {
            genderL.add(data);
        }

        ObservableList<String> ObList = FXCollections.observableArrayList(genderL);
        addStudents_gender.setItems(ObList);

    }
    private List<courseData> courseList = new ArrayList<>();

    public void addStudentsCourseList() {
        availableCourseList = availableCourseListData();
        courseList.addAll(availableCourseList);
        List<String> courseL = new ArrayList<>();

        for (courseData course : courseList) {
            courseL.add(course.getCourse());
        }

        ObservableList<String> ObList = FXCollections.observableArrayList(courseL);
        addStudents_course.setItems(ObList);

    }
    public ObservableList<Student> addStudentsListData() {
        ObservableList<Student> listStudent = FXCollections.observableArrayList();

        String sql = "SELECT * FROM students";

        try {
            connect = DBManager.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Student studentD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                studentD = new Student(
                        result.getInt("stud_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("gender"),
                        result.getDate("birth_date"),
                        result.getString("phone_num"),
                        result.getString("english_lvl"),
                        result.getString("course_id"),
                        result.getString("image"));

                listStudent.add(studentD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listStudent;
    }

    private ObservableList<Student> addStudentsListD;
    public void addStudentsShowListData() {
        addStudentsListD = addStudentsListData();

        addStudents_col_studentNum.setCellValueFactory(new PropertyValueFactory<>("stud_id"));
        addStudents_col_firstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        addStudents_col_lastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        addStudents_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addStudents_col_birth.setCellValueFactory(new PropertyValueFactory<>("birth_date"));
        addStudents_col_phone.setCellValueFactory(new PropertyValueFactory<>("phone_num"));
        addStudents_col_lvl.setCellValueFactory(new PropertyValueFactory<>("english_lvl"));
        addStudents_col_course.setCellValueFactory(new PropertyValueFactory<>("course_id"));

        addStudents_tableView.setItems(addStudentsListD);
    }
    public void addStudentsSelect() {
        Student studentD = addStudents_tableView.getSelectionModel().getSelectedItem();
        int num = addStudents_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addStudents_studentNum.setText(String.valueOf(studentD.getStud_id()));
        addStudents_firstName.setText(studentD.getFirst_name());
        addStudents_lastName.setText(studentD.getLast_name());
        addStudents_birth.setValue(LocalDate.parse(String.valueOf(studentD.getBirth_date())));
        addStudents_phone.setText(studentD.getPhone_num());

        String uri = "file:" + studentD.getImage();

        image = new Image(uri, 120, 149, false, true);
        addStudents_imageView.setImage(image);

        getData.path = studentD.getImage();
    }



    private double x = 0;
    private double y = 0;
    public void logout() {

        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                //HIDE YOUR DASHBOARD FORM
                logout.getScene().getWindow().hide();

                //LINK YOUR LOGIN FORM
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

            } else {
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void displayUsername() {
        username.setText(getData.username);
    }

    public void defaultNav(){
        home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
    }
    public void switchForm(ActionEvent event) {
        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            addTeachers_form.setVisible(false);
            availableCourse_form.setVisible(false);
            addStudents_form.setVisible(false);

            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            addTeachers_btn.setStyle("-fx-background-color:transparent");
            availableCourse_btn.setStyle("-fx-background-color:transparent");
            addStudents_btn.setStyle("-fx-background-color:transparent");

            homeDisplayTotalEnrolledStudents();
            homeDisplayMaleEnrolled();
            homeDisplayFemaleEnrolled();
            homeDisplayEnrolledMaleChart();
            homeDisplayFemaleEnrolledChart();
            homeDisplayTotalEnrolledChart();

        } else if (event.getSource() == addTeachers_btn) {
            home_form.setVisible(false);
            addTeachers_form.setVisible(true);
            availableCourse_form.setVisible(false);
            addStudents_form.setVisible(false);

            addTeachers_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            home_btn.setStyle("-fx-background-color:transparent");
            availableCourse_btn.setStyle("-fx-background-color:transparent");
            addStudents_btn.setStyle("-fx-background-color:transparent");

//            TO BECOME UPDATED ONCE YOU CLICK THE ADD STUDENTS BUTTON ON NAV
            addTeachersShowListData();
            addTeachersLvlList();
            addTeachersGenderList();
            //addStudentsCourseList();
            addTeachersSearch();

        } else if (event.getSource() == availableCourse_btn) {
            home_form.setVisible(false);
            addTeachers_form.setVisible(false);
            availableCourse_form.setVisible(true);
            addStudents_form.setVisible(false);

            availableCourse_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            addTeachers_btn.setStyle("-fx-background-color:transparent");
            home_btn.setStyle("-fx-background-color:transparent");
            addStudents_btn.setStyle("-fx-background-color:transparent");

            availableCourseShowListData();

        }
        else if (event.getSource() == addStudents_btn) {
            home_form.setVisible(false);
            addTeachers_form.setVisible(false);
            availableCourse_form.setVisible(false);
            addStudents_form.setVisible(true);

            addStudents_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            addTeachers_btn.setStyle("-fx-background-color:transparent");
            home_btn.setStyle("-fx-background-color:transparent");
            availableCourse_btn.setStyle("-fx-background-color:transparent");

        }
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();
        defaultNav();

        homeDisplayTotalEnrolledStudents();
        homeDisplayMaleEnrolled();
        homeDisplayFemaleEnrolled();
        homeDisplayEnrolledMaleChart();
        homeDisplayFemaleEnrolledChart();
        homeDisplayTotalEnrolledChart();

        // TO SHOW IMMIDIATELY WHEN WE PROCEED TO DASHBOARD APPLICATION FORM
        addTeachersShowListData();
        addTeachersGenderList();
        addTeachersLvlList();

        addStudentsShowListData();
        addStudentsGenderList();
        addStudentsLvlList();
        addStudentsCourseList();

        availableCourseShowListData();

    }

}
