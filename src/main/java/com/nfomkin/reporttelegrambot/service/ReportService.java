package com.nfomkin.firsttelegrambot.service;

import org.springframework.stereotype.Service;

@Service
public class ReportService {

    public String getInstruction() {
        return "instruction";
    }

    public String getTitleInstruction() {
        return "title instruction";
    }

    public String getBodyInstruction() {
        return "body instruction";
    }

    public String getPhotoInstruction() {
        return "photo instruction";
    }



    public boolean isDate(String message) {
        return false;
    }

    public boolean isCode(String message) {
        return true;
    }

    public boolean isRouteNumber(String message) {
        return true;
    }

    public boolean isDescription(String message) {
        return true;
    }

}
