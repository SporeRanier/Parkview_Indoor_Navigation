package edu.ipfw.parkview.indoornavigation;

/*Store user data
* TODO: create list of route/destination once location services are working
*/
class UserData {

    private String age;
    private String gender;

    UserData(String age, String gender) {

        this.age = age;
        this.gender = gender;
    }

    public String getAge(){
        return age;
    }

    public String getGender(){
        return gender;
    }
}
