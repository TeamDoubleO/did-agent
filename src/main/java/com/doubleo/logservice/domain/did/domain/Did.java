package com.doubleo.logservice.domain.did.domain;

import com.doubleo.logservice.domain.common.model.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
public class Did extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "did_id")
    private Long id;
}
