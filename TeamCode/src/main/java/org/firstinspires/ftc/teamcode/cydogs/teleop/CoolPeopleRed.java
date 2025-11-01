package org.firstinspires.ftc.teamcode.cydogs.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp
public class CoolPeopleRed extends CoolPeopleBlue {

    @Override
    public void runOpMode() {
        this.Team="Red";
        super.runOpMode();
    }
}
