package com.nisum.api.entity;

import com.nisum.api.dto.UserStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class User {

    @Id
    private String id;

    private String name;

    private String email;

    private String password;

    @CreationTimestamp
    private LocalDateTime lastLogin;

    private String token;

    private UserStatus isActive;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Phone> phoneList;

}
