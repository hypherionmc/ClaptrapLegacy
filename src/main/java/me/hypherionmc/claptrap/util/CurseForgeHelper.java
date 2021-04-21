package me.hypherionmc.claptrap.util;

import com.therandomlabs.curseapi.CurseAPI;
import com.therandomlabs.curseapi.CurseException;
import com.therandomlabs.curseapi.project.CurseProject;

import java.util.Optional;

public class CurseForgeHelper {

    public static boolean curseProjectExists(Integer id) {
        try {
            Optional<CurseProject> project = CurseAPI.project(id);
            return project.isPresent();
        } catch (CurseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static CurseProject getProjectInfo(Integer id) {
        try {
            Optional<CurseProject> project = CurseAPI.project(id);
           if (project.isPresent()) {
               return project.get();
           }
        } catch (CurseException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
