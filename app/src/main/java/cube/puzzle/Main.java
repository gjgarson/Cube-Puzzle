package cube.puzzle;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

class Cube {
    String initialConstruction = "";
    ArrayList<String> transformedConstructions = new ArrayList<>();

    Cube(int[] sides) {

        for (int side : sides) {
            initialConstruction += side;
        }

        transformedConstructions = transformCube();
    } //end cube constructor



    ArrayList<String> transformCube() {
        ArrayList<String> transformations = new ArrayList<>();


        //generate all 24 transformations
        transformations.add(0, initialConstruction);
        for (int i = 1; i < 24; i++) {
            if (i % 4 != 0) {
                transformations.add(rotateX(transformations.get(i - 1)));
            }
            else if (i == 4 || i == 12 || i == 20) {
                transformations.add(rotateY(transformations.get(i - 4)));
            }
            else if (i == 8 || i == 16) {
                transformations.add(rotateZ(transformations.get(i - 4)));
            }
        }


        //get rid of duplicates
        Set<String> uniqueTransformations = new HashSet<>(transformations);
        transformations = new ArrayList<>(uniqueTransformations);

        return transformations;
    } //end transformCube method



    String rotateX(String construction) {
        String newConstruction = "";

        newConstruction += construction.charAt(3);
        newConstruction += construction.charAt(0);
        newConstruction += construction.charAt(1);
        newConstruction += construction.charAt(2);
        newConstruction += construction.charAt(7);
        newConstruction += construction.charAt(4);
        newConstruction += construction.charAt(5);
        newConstruction += construction.charAt(6);
        newConstruction += construction.charAt(11);
        newConstruction += construction.charAt(8);
        newConstruction += construction.charAt(9);
        newConstruction += construction.charAt(10);

        return newConstruction;
    } //end rotateX method

    String rotateY(String construction) {
        String newConstruction = "";

        newConstruction += construction.charAt(8);
        newConstruction += construction.charAt(4);
        newConstruction += construction.charAt(0);
        newConstruction += construction.charAt(7);
        newConstruction += construction.charAt(9);
        newConstruction += construction.charAt(1);
        newConstruction += construction.charAt(3);
        newConstruction += construction.charAt(11);
        newConstruction += construction.charAt(10);
        newConstruction += construction.charAt(5);
        newConstruction += construction.charAt(2);
        newConstruction += construction.charAt(6);

        return newConstruction;
    } //end rotateY method

    String rotateZ(String construction) {
        String newConstruction = "";

        newConstruction += construction.charAt(7);
        newConstruction += construction.charAt(3);
        newConstruction += construction.charAt(6);
        newConstruction += construction.charAt(11);
        newConstruction += construction.charAt(0);
        newConstruction += construction.charAt(2);
        newConstruction += construction.charAt(10);
        newConstruction += construction.charAt(8);
        newConstruction += construction.charAt(4);
        newConstruction += construction.charAt(1);
        newConstruction += construction.charAt(5);
        newConstruction += construction.charAt(9);

        return newConstruction;
    } //end rotateZ method
} //end cube class

public class Main {
    static boolean sidesLeft(boolean[] visited) {
        //check if there are any sides left to visit
        for (boolean side : visited) {
            if (side == false) {
                return true;
            }
        }
        return false;
    } //end sidesLeft method

    static int[] unpackSides(String construction) {
        int[] sides = new int[12];

        for (int side = 0; side < 12; side++) {
            if (construction.charAt(side) == '1') {
                sides[side] = 1;
            }
            else {
                sides[side] = 0;
            }
        }

        return sides;
    } //end unpackSides method



    static boolean checkConnected(int sides[]) {
        boolean connected = true;

        //list out sides that need to be checked
        boolean[] visited = new boolean[12];
        int currentSide = -1;

        for (int i = 0; i < sides.length; i++) {
            if (sides[i] == 1) {
                visited[i] = false;
            }
            else {
                visited[i] = true;
            }
        }


        ArrayList<Integer> path = new ArrayList<>();

        //list asjacencies
        int[][] adjacencies = {
            {1, 3, 4 ,7}, //s0
            {0, 2, 4, 5}, //s1
            {1, 3, 5, 6}, //s2
            {0, 2, 6, 7}, //s3
            {0, 1, 8, 9}, //s4
            {1, 2, 9, 10}, //s5
            {2, 3, 10, 11}, //s6
            {0, 3, 8, 11}, //s7
            {4, 7, 9, 11}, //s8
            {4, 5, 8, 10}, //s9
            {5, 6, 9, 11}, //s10
            {6, 7, 8, 10}  //s11
        };

        
        for (int i = 0; i < 12; i++) {
            if (visited[i] == false) {
                currentSide = i;
                break;
            }
        }

        if (currentSide == -1) {
            return false; //no sides to check (empty cube)
        }

        visited[currentSide] = true;
        path.add(currentSide);

        boolean sideFound;

        while (sidesLeft(visited)) {
            sideFound = false;
            for (int i : adjacencies[currentSide]) {
                if (visited[i] == false) {
                    sideFound = true;
                    currentSide = i;
                    visited[currentSide] = true;
                    path.add(i);
                    break;
                }
            }

            if (!sideFound) {
                if (path.size() > 1) {
                    path.remove(path.size() - 1);
                    currentSide = path.get(path.size() - 1);
                }
                else {
                    connected = false;
                    break;
                }
            }
        }
        

        return connected;
    } //end checkConnected method


    static boolean check3D(int[] sides) {
        int s0 = sides[0];
        int s1 = sides[1];
        int s2 = sides[2];
        int s3 = sides[3];
        int s4 = sides[4];
        int s5 = sides[5];
        int s6 = sides[6];
        int s7 = sides[7];
        int s8 = sides[8];
        int s9 = sides[9];
        int s10 = sides[10];
        int s11 = sides[11];

        boolean x = (s0 + s2 + s8 + s10) > 0;
        boolean y = (s4 + s5 + s6 + s7) > 0;
        boolean z = (s1 + s3 + s9 + s11) > 0;

        return x && y && z;
    } //end check3D method


    static ArrayList<Cube> getCubeVariants() {
        ArrayList<Cube> cubeVariants = new ArrayList<>();

        for (int s0 = 0; s0 <= 1; s0++) {
            for (int s1 = 0; s1 <= 1; s1++) {
                for (int s2 = 0; s2 <= 1; s2++) {
                    for (int s3 = 0; s3 <= 1; s3++) {
                        for (int s4 = 0; s4 <= 1; s4++) {
                            for (int s5 = 0; s5 <= 1; s5++) {
                                for (int s6 = 0; s6 <= 1; s6++) {
                                    for (int s7 = 0; s7 <= 1; s7++) {
                                        for (int s8 = 0; s8 <= 1; s8++) {
                                            for (int s9 = 0; s9 <= 1; s9++) {
                                                for (int s10 = 0; s10 <= 1; s10++) {
                                                    for (int s11 = 0; s11 <= 1; s11++) {
                                                        cubeVariants.add(new Cube(new int[]{s0, s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11}));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        cubeVariants.remove(cubeVariants.size() - 1); //remove full cube

        return cubeVariants;
    } //end getCubeVariants method


    static ArrayList<Cube> filterCubes(ArrayList<Cube> cubeVariants) {
        ArrayList<Cube> uniqueCubes = new ArrayList<>();
        Set<String> seenConstructions = new HashSet<>();

        for (Cube cube : cubeVariants) {
            //check if cube is 3d
            if (!check3D(unpackSides(cube.initialConstruction))) {
                continue;
            }

            //check if cube is connected
            if (!checkConnected(unpackSides(cube.initialConstruction))) {
                continue;
            }


            //check if cube is unique
            boolean isUnique = true;
            for (String transformation : cube.transformedConstructions) {
                if (seenConstructions.contains(transformation)) {
                    isUnique = false;
                    break;
                }
            }

            if (isUnique) {
                uniqueCubes.add(cube);
                seenConstructions.addAll(cube.transformedConstructions);
            }
        }

        return uniqueCubes;
    } //end filterCubes method


    public static void main(String[] args) {
        ArrayList<Cube> cubes = getCubeVariants();
        System.out.println("cubes generated, current count: " + cubes.size());

        cubes = filterCubes(cubes);
        System.out.println("all unique cubes count: " + cubes.size());
    } //end main method
} //end Main class