/*
Name: Anthony Garza
Project: FaceDraw
Purpose: To create the FaceDraw application for Sprint 3. This is to create a group of Faces. The # of faces 
        should have a random number between 3-10, and should be randomized between smiles, frowns, and in-between.
        This will (hopefully) also have a unique feature of my own choice implemented successfully.
        DISCLAIMER: This application utilizes the ShapesLibrary Library, which I am not the author of.
*/

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Random;
import java.util.ArrayList;
import java.awt.Color;

class oDraw extends Oval {
        private Boolean drawOvalFilledGreen;
        public void setDrawOvalFilledGreen(){
                drawOvalFilledGreen = true;
        }
        private Boolean drawOvalFilledBlue;
        public void setDrawOvalFilledBlue(){
                drawOvalFilledBlue = true;
        }

        //override default constructor first
        public oDraw(){
                super(0,0,0,0);
                drawOvalFilledGreen = false;
                drawOvalFilledBlue = false;
        }

        //non-default constructor
        public oDraw(int xCoord, int yCoord, int widthIn, int heightIn){
                super(xCoord, yCoord, widthIn, heightIn);
                drawOvalFilledGreen = false;
                drawOvalFilledBlue = false;
        }
    
        public void paintComponent(Graphics graphic){
                graphic.drawOval(getPositionX(),getPositionY(), getWidth(), getHeight());
                if(drawOvalFilledGreen){
                        graphic.setColor(Color.GREEN);
                graphic.fillOval(getPositionX()+1, getPositionY()+1, getWidth()-2, getHeight()-2);
                graphic.setColor(Color.black);
        }
        if(drawOvalFilledBlue){
                graphic.setColor(Color.BLUE);
                graphic.fillOval(getPositionX()+1, getPositionY()+1, getWidth()-2, getHeight()-2);
                graphic.setColor(Color.black);
        }
        
    }
}

class Face extends oDraw {
        private oDraw eyeL;
        private oDraw eyeR;
        private Random mouthRand = new Random();
        private int mouthBehavior = mouthRand.nextInt(99);
        
        public Face(){
                super(0,0,0,0);
                eyeL = new oDraw(0,0,0,0);
                eyeR = new oDraw(0,0,0,0);
        }

        public Face(int widthIn, int heightIn){
                super(0,0,0,0);
                eyeL = new oDraw(0,0,0,0);
                eyeR = new oDraw(0,0,0,0);
        }

        public Face(int xCoord, int yCoord, int widthIn, int heightIn, int mouthBehavior){
                super(xCoord, yCoord, widthIn, heightIn);

                int eyeHeight = heightIn / 9;
                int eyeWidth = eyeHeight;
                int eyePositionXL = xCoord + (widthIn / 3) - (eyeWidth);
                int eyePositionY = yCoord + (heightIn / 3) - (eyeHeight /2);
                int eyePositionXR = xCoord + (widthIn / 2) - (eyeWidth / 9);

                eyeL = new oDraw(eyePositionXL,eyePositionY,eyeWidth,eyeHeight);
                eyeR = new oDraw(eyePositionXR,eyePositionY,eyeWidth,eyeHeight);

                eyeL.setDrawOvalFilledGreen();
                eyeR.setDrawOvalFilledBlue();
        }

        //required getters + setters NOT IN USE!!! but satisfies requirements
        //setter for xCoord
        public final void setxCoord (int xCoordIn){
                xCoord = xCoordIn;
        }
        //getter for xCoord
        public int getxCoord (){
                return xCoord;
        }
        private int xCoord;

        //setter for yCoord
        public final void setyCoord (int yCoordIn){
                yCoord =   yCoordIn;
        }
        //getter for yCoord
        public int getyCoord(){
                return yCoord;
        }
        private int yCoord;

        //setter for width
        public final void setWidthFace(int widthIn){
                width = widthIn;
        } 
        //getter for width
        public int getWidthFace(){
                return width;
        }
        private int width;

        //setter for height
        public final void setHeightFace(int heightIn){
                height = heightIn;
        }
        //getter for height
        public int getHeightFace(){
                return height;
        }
        private int height;
        //setter for smileStatus
        public final void setSmileFace(int smileStatusIn){
                smileStatus = smileStatusIn;
        }
        //getter for smileStatus
        public int getSmileStatus(){
                return smileStatus;
        }
        private int smileStatus;


        public void paintComponent(Graphics graphic){
                super.paintComponent(graphic);
                eyeL.paintComponent(graphic);
                eyeR.paintComponent(graphic);

                if(mouthBehavior <= 33) {
                        //frown
                        graphic.drawArc(getPositionX(), getPositionY() + (getHeight() / 2), getWidth()-10, getHeight()-10, 45, 90);
                }else if(mouthBehavior > 33 && mouthBehavior <= 66){
                        //in between mouth
                        graphic.drawLine(getPositionX() + 10 , getPositionY() + (getHeight() / 2) ,  getPositionX() + (getWidth() - 10), getPositionY() + (getHeight() / 2));
                }else{
                        //grin
                        graphic.drawArc(getPositionX() + 10, getPositionY(), getWidth()-20, getHeight()-20, 180, 180);
                }
        }

        public String toString(){
                String superString = super.toString();
                return String.format("New Face Created");
        }

}

class FacePanel extends JPanel {
        private Face myFace;
        private ArrayList<Face> FaceList;


        public void setFaces(ArrayList<Face> facesIn){
                FaceList = facesIn;
        }

        FacePanel(){
                super();
        }

        FacePanel(ArrayList<Face> facesIn) {
                setFaces(facesIn);
        }

        public void paintComponent(Graphics graphic){
                super.paintComponent(graphic);
                for (Face f : FaceList){
                        f.paintComponent(graphic);
                }
        }
}
class FaceFrame extends JFrame{
        private FacePanel myFacePanel;
        public FaceFrame(ArrayList<Face> facesIn){
                setDefaultLookAndFeelDecorated(true);
                setBounds(100,100,900,650);
                setTitle("FaceDraw AG");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                

                FacePanel myFacePanel = new FacePanel(facesIn);

                add(myFacePanel);
                setVisible(true);
        }
}

public class FaceDraw{

        public static void main(String[] args){
                System.out.println("Starting FaceDraw application...");
                
                ArrayList<Face> FaceList = new ArrayList<Face>();
                Random randNum = new Random();
                int faceCap = randNum.nextInt(10 - 3 + 1) + 3;


                for (int i = 0 ; i < faceCap; i++){
                        Random mouthRand = new Random();
                        int mouthBehavior;
                        //randomize dimensions for face 
                        int randXCoord = randNum.nextInt(600) + 90;
                        int randYCoord = randNum.nextInt(400) + 90;
                        int randWidth = randNum.nextInt(150) + 50;
                        int randHeight = randNum.nextInt(150) + 50;
                        //randomize mouth behavior
                        mouthBehavior = mouthRand.nextInt(99);
                        Face myFace = new Face(randXCoord,randYCoord,randWidth,randHeight, mouthBehavior);
                        FaceList.add(myFace);
                        System.out.println(myFace.toString());
                }
                //extra line to confirm # of faces randomly created
                System.out.println("Number of Faces:" + FaceList.size());
                FaceFrame myFrame = new FaceFrame(FaceList);
        }
}