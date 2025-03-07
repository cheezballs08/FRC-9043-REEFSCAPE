// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.units;

import java.util.List;
import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.simulation.PhotonCameraSim;
import org.photonvision.simulation.SimCameraProperties;
import org.photonvision.simulation.VisionSystemSim;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform3d;
import frc.robot.constants.VisionConstants;
import frc.robot.utils.CameraPosition;

public class VisionProcessingUnit {

  private static VisionProcessingUnit frontUnit = new VisionProcessingUnit(VisionConstants.frontCameraName, VisionConstants.robotToFrontCameraTransform, VisionConstants.frontCameraProperties);

  private static VisionProcessingUnit leftUnit = new VisionProcessingUnit(VisionConstants.leftCameraName, VisionConstants.robotToLeftCameraTransform, VisionConstants.leftCameraProperties);

  private static VisionProcessingUnit rightUnit = new VisionProcessingUnit(VisionConstants.rightCameraName, VisionConstants.robotToRightCameraTransform, VisionConstants.rightCameraProperties);
  
  private static VisionSystemSim simulation;

  static {
    simulation = new VisionSystemSim(VisionConstants.simulationName);

    simulation.addAprilTags(VisionConstants.fieldLayout);

    simulation.addCamera(frontUnit.getCameraSimulation(), VisionConstants.robotToFrontCameraTransform);

    simulation.addCamera(leftUnit.getCameraSimulation(), VisionConstants.robotToLeftCameraTransform);

    simulation.addCamera(rightUnit.getCameraSimulation(), VisionConstants.robotToRightCameraTransform);
  }

  private PhotonCamera camera;

  private PhotonTrackedTarget bestTarget;

  private List<PhotonTrackedTarget> seenSargets;

  private PhotonPipelineResult result;

  private PhotonPoseEstimator poseEstimator;

  private EstimatedRobotPose estimatedPose;

  private Optional<EstimatedRobotPose> estimateOptional;

  private PhotonCameraSim cameraSimulation;

  private boolean isEstimationEnabled;

  private VisionProcessingUnit(String cameraName, Transform3d robotToCamTransform, SimCameraProperties cameraProperties) {
    camera = new PhotonCamera(cameraName);

    poseEstimator = new PhotonPoseEstimator(VisionConstants.fieldLayout, VisionConstants.poseEstimationStrategy, robotToCamTransform);
  
    cameraSimulation = new PhotonCameraSim(camera, cameraProperties);

    cameraSimulation.enableDrawWireframe(true);
  }

  public static VisionProcessingUnit getUnit(CameraPosition position) {
    if (position == CameraPosition.Front) {
      return frontUnit;
    } else if (position == CameraPosition.Left) {
      return leftUnit;
    } else {
      return rightUnit;
    }
  }

  public static VisionSystemSim getSimulation() {
    return simulation;
  }

  public void updateResult() {
    List<PhotonPipelineResult> results = camera.getAllUnreadResults();
    
    if (results.isEmpty()) {
      this.result = new PhotonPipelineResult();
      return;
    }

    this.result = results.get(results.size() - 1);
  }

  public void updateTargets() {
    this.seenSargets = result.getTargets();
    this.bestTarget = result.getBestTarget();
  }

  public void update() {
    this.updateResult();
    this.updateTargets();
    this.updatePoseEstimator();
  }

  public PhotonTrackedTarget getBestTarget() {
    return bestTarget;
  }

  public List<PhotonTrackedTarget> getTargets() {
    return seenSargets;
  }

  public PhotonPipelineResult getResult() {
    return result;
  }

  public boolean isSeen(int id) {
    for (PhotonTrackedTarget target : seenSargets) {
      if (target.getFiducialId() == id) {
        return true;
      }
    }
    return false;
  }

  public PhotonTrackedTarget getTarget(int id) {
    for (PhotonTrackedTarget target : seenSargets) {
      if (target.getFiducialId() == id) {
        return target;
      }
    }
    return null;
  }

  public void updatePoseEstimator() {
    // TODO: Pose estimator direk böyle çalışıyor mu? Kalibreye ihtiyacı yok mudur?
    estimateOptional = poseEstimator.update(result); 

    if (estimateOptional.isPresent()) {
      this.estimatedPose = estimateOptional.get();
    } else {
      this.estimatedPose = null;
    }
  }

  public boolean canEstimatePose() {
    return estimateOptional.isPresent() && isEstimationEnabled;
  }

  public EstimatedRobotPose getEstimate() {
    return estimatedPose;
  }

  public Pose2d getEstimatedPose2d() {
    return estimatedPose.estimatedPose.toPose2d();
  }

  public static void updateSimulation(Pose2d robotSimPose) {
    simulation.update(robotSimPose);
  }

  private PhotonCameraSim getCameraSimulation() {
    return cameraSimulation;
  }

  public void disableVisionEstimation(boolean disable) {
    if (disable) {
      this.isEstimationEnabled = false;
    }
    else {
      this.isEstimationEnabled = true;
    }
  }
}
