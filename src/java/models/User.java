    package models;

    import java.sql.Timestamp;

    public class User {
        private int id;
        private String fullName;
        private String gender;
        private String email;
        private String mobile;
        private String address;
        private String password;
        private String role;
        private String status;
        private Timestamp createdAt;
        private Timestamp updatedAt;
        private String avatar;  // Thêm thuộc tính avatar

        // Constructors
        public User() {}

        public User(String fullName, String gender, String email, String mobile, String address, String password, String role, String status, String avatar) {
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.password = password;
        this.role = role;
        this.status = status;
        this.avatar = avatar;
    }

    // Constructor với id
    public User(int id, String fullName, String gender, String email, String mobile, String address, String password, String role, String status, String avatar) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.password = password;
        this.role = role;
        this.status = status;
        this.avatar = avatar;
    }


        // Getters and Setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Timestamp getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Timestamp createdAt) {
            this.createdAt = createdAt;
        }

        public Timestamp getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Timestamp updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", fullName='" + fullName + '\'' +
                    ", gender='" + gender + '\'' +
                    ", email='" + email + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", address='" + address + '\'' +
                    ", password='" + password + '\'' +
                    ", role='" + role + '\'' +
                    ", status='" + status + '\'' +
                    ", createdAt=" + createdAt +
                    ", updatedAt=" + updatedAt +
                    ", avatar='" + avatar + '\'' +
                    '}';
        }
    }
