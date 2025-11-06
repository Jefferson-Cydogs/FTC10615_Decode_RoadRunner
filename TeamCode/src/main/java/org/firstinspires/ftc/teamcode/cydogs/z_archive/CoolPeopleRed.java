package org.firstinspires.ftc.teamcode.cydogs.z_archive;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
@Disabled
public class CoolPeopleRed extends CoolPeopleBlue {

    @Override
    public void runOpMode() {
        this.Team="Red";
        super.runOpMode();
    }
}
