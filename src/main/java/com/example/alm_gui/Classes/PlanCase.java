package com.example.alm_gui.Classes;
public class PlanCase {
    private int id;
    private int id_test_plan;
    private int id_test_case;
    private String execution;
    public PlanCase(int id_test_plan, int id_test_case, String execution){
        this.id_test_case=id_test_case;
        this.id_test_plan=id_test_plan;
        this.execution=execution;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getExecution() {
        return execution;
    }

    public int getId_test_case() {
        return id_test_case;
    }

    public int getId_test_plan() {
        return id_test_plan;
    }

    public void setExecution(String execution) {
        this.execution = execution;
    }

    public void setId_test_case(int id_test_case) {
        this.id_test_case = id_test_case;
    }

    public void setId_test_plan(int id_test_plan) {
        this.id_test_plan = id_test_plan;
    }

}
