package com.aiplanner.aiprojectplanner.rag;

import lombok.Data;
import java.util.List;

@Data
public class Embedding {

    private String text;

    private List<Double> vector;

}