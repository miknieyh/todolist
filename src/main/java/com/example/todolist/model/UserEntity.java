package com.example.todolist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class UserEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    private String id; //사용자 고유 id

    @Column(nullable = false)
    private String username; //사용자 이름
    @Column(nullable = false)
    private String email; //사용자 이메일 id와 같은 역할을 한다.
    @Column(nullable = false)
    private String password; //패스워드

}
