package hu.oszkarpap.dev.android.omsz.omszapp001.guide.parameters;

import android.widget.SeekBar;

public class ParametersMed {
    private String medLabel;

    private String MedDose;

    private SeekBar seekBar;

    public ParametersMed(String medLabel, String medDose, SeekBar seekBar) {
        this.medLabel = medLabel;
        MedDose = medDose;
        this.seekBar = seekBar;
    }

    public ParametersMed(String medLabel, String medDose) {
        this.medLabel = medLabel;
        MedDose = medDose;
    }

    public SeekBar getSeekBar() {
        return seekBar;
    }

    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }

    public String getMedLabel() {
        return medLabel;
    }

    public void setMedLabel(String medLabel) {
        this.medLabel = medLabel;
    }

    public String getMedDose() {
        return MedDose;
    }

    public void setMedDose(String medDose) {
        MedDose = medDose;
    }
}
