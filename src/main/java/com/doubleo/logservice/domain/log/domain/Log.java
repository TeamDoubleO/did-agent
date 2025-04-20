package com.doubleo.logservice.domain.log.domain;

import com.doubleo.logservice.domain.common.model.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
public class Log extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;
}
