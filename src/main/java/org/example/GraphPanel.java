package org.example;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class GraphPanel extends JPanel {
    private Particle particle;
    private final List<double[]> trajectory = new ArrayList<>();
    private final double SCALE_PARAM = 0.20;

    public GraphPanel() {
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g.create();

        try {
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            double scale = 1.0E8;

            int width = getWidth();
            int height = getHeight();

            int centerX = width / 2;
            int centerY = height / 2;

            double maxX = 0;
            double maxY = 0;

            for (double[] point : trajectory){
                maxX = Math.max(maxX, Math.abs(point[0]));
                maxY = Math.max(maxY, Math.abs(point[1]));
            }

            if (maxX > 0 || maxY > 0) {
                double scaleX = maxX > 0 ? width * SCALE_PARAM / maxX : Double.POSITIVE_INFINITY;
                double scaleY = maxY > 0 ? width * SCALE_PARAM / maxY : Double.POSITIVE_INFINITY;

                scale = Math.min(scaleX, scaleY);
            } else {
                scale = 1.0E8;
            }

            graphics2D.setColor(Color.LIGHT_GRAY);
            graphics2D.setStroke(new BasicStroke(1));

            graphics2D.drawLine(
                    0, centerY, width, centerY
            );

            graphics2D.drawLine(
                    centerX, 0, centerX, height
            );

            graphics2D.setColor(Color.BLACK);

            graphics2D.drawString("X", width - 20, centerY - 10);
            graphics2D.drawString("Y", centerX + 10, 20);

            if (trajectory.size() > 1) {

                Path2D path = new Path2D.Double();

                double[] first = trajectory.getFirst();

                path.moveTo(
                        centerX + first[0] * scale,
                        centerY - first[1] * scale
                );

                for (int i = 1; i < trajectory.size(); i++) {

                    double[] point = trajectory.get(i);

                    path.lineTo(
                            centerX + point[0] * scale,
                            centerY - point[1] * scale
                    );
                }

                graphics2D.setColor(Color.BLUE);

                graphics2D.setStroke(
                        new BasicStroke(
                                2,
                                BasicStroke.CAP_ROUND,
                                BasicStroke.JOIN_ROUND
                        )
                );

                graphics2D.draw(path);
            }

            if (particle != null) {

                int x = (int) (centerX + particle.getX() * scale);

                int y = (int) (centerY - particle.getY() * scale);

                graphics2D.setColor(Color.RED);

                graphics2D.fillOval(
                        x - 6, y - 6, 12, 12
                );
            }

        } finally {
            graphics2D.dispose();
        }
    }

    public void setParticle(Particle particle) {
        this.particle = particle;
        if(particle != null){
            trajectory.add(new double[]{
                    particle.getX(),
                    particle.getY()
            });
        }
        this.repaint();
    }

    public void clearTrajectory() {
        trajectory.clear();
        particle = null;
        this.repaint();
    }

}
