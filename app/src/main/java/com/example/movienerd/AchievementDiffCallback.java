package com.example.movienerd;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class AchievementDiffCallback extends DiffUtil.Callback {
    private final List<Achievement> oldAchievementList;
    private final List<Achievement> newAchievementList;

    public AchievementDiffCallback(List<Achievement> oldAchievementList, List<Achievement> newAchievementList) {
        this.oldAchievementList = oldAchievementList;
        this.newAchievementList = newAchievementList;
    }

    @Override
    public int getOldListSize() {
        return this.oldAchievementList.size();
    }

    @Override
    public int getNewListSize() {
        return newAchievementList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldAchievementList.get(oldItemPosition) == newAchievementList.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Achievement oldAchievement = oldAchievementList.get(oldItemPosition);
        final Achievement newAchievement = newAchievementList.get(newItemPosition);

        return oldAchievement.getTitle().equals(newAchievement.getTitle());
    }
}
