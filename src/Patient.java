public class Patient {
    private String id;
    private String name;
    private String age;
    private String gender;
    private String disease;
    private String doctor;
    private String phone;

    public Patient(String id, String name, String age, String gender, String disease, String doctor, String phone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.disease = disease;
        this.doctor = doctor;
        this.phone = phone;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getAge() { return age; }
    public String getGender() { return gender; }
    public String getDisease() { return disease; }
    public String getDoctor() { return doctor; }
    public String getPhone() { return phone; }

    public String toFileString() {
        return id + "|" + name + "|" + age + "|" + gender + "|" + disease + "|" + doctor + "|" + phone;
    }

    public static Patient fromFileString(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length >= 7) {
            return new Patient(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
        }
        return null;
    }
}
