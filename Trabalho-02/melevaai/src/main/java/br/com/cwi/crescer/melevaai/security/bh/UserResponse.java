package br.com.cwi.crescer.melevaai.security.bh;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponse {

    private String email;
    private String firstName;
    private String lastName;
    private List<String> roles;
}
