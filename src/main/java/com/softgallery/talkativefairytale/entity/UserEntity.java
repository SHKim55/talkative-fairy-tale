package com.softgallery.talkativefairytale.entity;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="user")
public class UserEntity {
    @Id
    @NotNull
    private String username;

    @Column
    @NotNull
    private String password;
}
