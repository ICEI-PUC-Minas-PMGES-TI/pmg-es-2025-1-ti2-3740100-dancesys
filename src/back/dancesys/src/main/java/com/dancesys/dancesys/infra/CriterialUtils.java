package com.dancesys.dancesys.infra;

import jakarta.persistence.criteria.Root;

import jakarta.persistence.criteria.Path;

public class CriterialUtils {
    public static Path<?> getPath(Root<?> root, String caminho) {
        String[] partes = caminho.split("\\.");
        Path<?> path = root;
        for (String parte : partes) {
            path = path.get(parte);
        }

        return path;
    }
}
