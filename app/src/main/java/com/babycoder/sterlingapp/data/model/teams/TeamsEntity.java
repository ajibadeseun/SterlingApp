package com.babycoder.sterlingapp.data.model.teams;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.babycoder.sterlingapp.data.model.competitions.CompetitionsEntity;

@Entity(tableName = "teams",
        indices = {
            @Index(value = "comp_id"),
            @Index(value = "id", unique = true)},
        foreignKeys = @ForeignKey(entity = CompetitionsEntity.class,
            parentColumns = "id", childColumns = "comp_id",
            onDelete = ForeignKey.CASCADE))
public class TeamsEntity {

    @ColumnInfo(name = "no")
    @PrimaryKey(autoGenerate = true)
    private int baseId;

    @ColumnInfo(name = "comp_id")
    private Integer teamCompetitionId;

    @ColumnInfo(name = "id")
    private Integer teamId;

    @ColumnInfo(name = "name")
    private String teamName;

    @ColumnInfo(name = "logo")
    private String teamLogo;

    public int getBaseId() {
        return baseId;
    }

    public void setBaseId(int baseId) {
        this.baseId = baseId;
    }

    public Integer getTeamCompetitionId() {
        return teamCompetitionId;
    }

    public void setTeamCompetitionId(Integer teamCompetitionId) {
        this.teamCompetitionId = teamCompetitionId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamLogo() {
        return teamLogo;
    }

    public void setTeamLogo(String teamLogo) {
        this.teamLogo = teamLogo;
    }
}
