package edu.ipfw.parkview.indoornavigation;

/*Store user data*/
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
