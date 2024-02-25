package model;

import java.util.Objects;

public record User(String username, String password, String email){
    public boolean isValid(){
        return this.username() != null &&
                this.password() != null &&
                this.email() != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }
};