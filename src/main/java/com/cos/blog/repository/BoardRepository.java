package com.cos.blog.repository;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;



public interface BoardRepository extends JpaRepository<Board, Integer> {
}

