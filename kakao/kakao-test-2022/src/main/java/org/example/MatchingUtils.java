package org.example;

import org.example.dto.MatchingDTO;

import java.util.Comparator;

public class MatchingUtils {

    public static class BasicMatchingCompare implements Comparator<MatchingDTO> {
        @Override
        public int compare(MatchingDTO matchingDTO, MatchingDTO t1) {
            if (matchingDTO.getFrom() > 10 && t1.getFrom() > 10) {
                return matchingDTO.getGrade() - t1.getGrade();
            }
            else if (matchingDTO.getFrom() > 10) return -1;
            else if (t1.getFrom() > 10) return 1;

            return matchingDTO.getGrade() - t1.getGrade();
        }
    }
}
