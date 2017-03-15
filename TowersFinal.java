/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.others.algoproject;
import java.util.Arrays;
import java.util.Scanner;
//import org.apache.commons.lang3.ArrayUtils;
/**
 *
 * @author sanjanaagarwal
 */
public class TowersFinal {
    static Scanner input = new Scanner(System.in);
    static long arrayH[],k, vectorV[];
    static long newValueOfn,n;
    static long[][] dpMatrix;
    static long m2Matrix[][] = new long[15][15];
    static long RMatrix[] = new long[15];
    /*
        Setting the available block sizes array
    */
    static void setAvailableBlocks(long availableBlocks[])
    {
        for(int i=0;i<availableBlocks.length;i++)
            availableBlocks[i] = input.nextLong();
    }
    /*
        Setting H array, as per the available block size. If block of a size
        is available, set it to 1, else 0.
    */
    static long[] setArrayH(long availableBlocks[], long arrayH[])
    {
        for(int i=0;i<availableBlocks.length;i++)
            for(int j=0;j<=arrayH.length;j++)
                if(j==availableBlocks[i])
                    arrayH[j-1]=1;         
        return arrayH;
    }
    /*
        Initializing matrix where f(0) will always be 1. 
    */
    static void initializeDPMatrix(long dpMatrix[][])
    {
        
            for(int j=0;j<dpMatrix[0].length;j++)
                dpMatrix[0][0] = 1;
    }
    /*
        Using Dynamic Programming, we find values of f(1),f(2),...,f(n)
        where f(n) = h1*f(n-1)+h2*f(n-2)+...hn*f(0)
    */
    static void setDPMatrix(long dpMatrix[][], long arrayH[])
    {
            for(int j=1;j<dpMatrix[0].length;j++)
            {
                for(int p=1;p<=j;p++)
                {
                    dpMatrix[0][j]+=arrayH[p-1]*dpMatrix[0][j-p];
                }
            }
    }
    /*
        Matrix Multiplication
    */
    static long[][] matrixMultiply(long[][] a, long [][] b)
    {
        long c[][] = new long[a.length][a.length];
        for(int i=0;i<a.length;i++)
        {
            for(int j=0;j<b.length;j++)
            {
                for(int p=0;p<b.length;p++)
                {
                    c[i][j]+=a[i][p]*b[p][j];                    
                }
                c[i][j] = c[i][j] % (long)(Math.pow(10, 9)+7);
            }             
        }          
        return c;
    }
    
    /*
        After Matrix multiplication, to multiply it to several powers, for
        exponentiation by squaring, like (x^2)^8. So x^2 is done to a power
        of 8 times. 
    */
    static long[][] powerMatrixMultiply(long[][] a, long power)
    {   
        long[][] result = a;
        for(int i=1;i<power;i++)
        {
            result = matrixMultiply(result, a);
        }
            
        return result;
    }
    
    static void printMatrix(long[][] matrix)
    {
    }
    /*
        Calculation of R, where R = M*V.
    */
    static long[] matrixMultiply1X2(long[] VectorV, long[][] m2Matrix)
    {
        long[] result = new long[m2Matrix.length];
        
        for(int i=0;i<m2Matrix.length;i++)
        {
            for(int j=0;j<m2Matrix.length;j++)
                    result[i]+= m2Matrix[i][j]*VectorV[j];
        }
        return result;
    }
    /*
        Calculation of h1...h15
    */
    static void forNLTE15(long n, long availableBlocks[])
    {
        arrayH = new long[(int)n];
        arrayH = setArrayH(availableBlocks,arrayH);
        dpMatrix = new long[1][(int)n+1];
        initializeDPMatrix(dpMatrix);
        setDPMatrix(dpMatrix,arrayH);
        
    }
    /*
        Exponentiation by squaring
    */
    static long[][] checkForNewValueOfn(long newValueOfn, long[][] mMatrix)
    {
            if(newValueOfn == 0)
            {
                long[][] i = {{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                               {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
                               {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
                               {0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
                               {0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
                               {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
                               {0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
                               {0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
                               {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0},
                               {0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
                               {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0},
                               {0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
                               {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
                               {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
                               {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                               };
                return i;
            }
            if(newValueOfn == 1)
            {
                printMatrix(mMatrix);
                m2Matrix =  mMatrix;
                return (m2Matrix);
                
            }
            
            else if(newValueOfn % 2 == 0)
            {
                m2Matrix =  matrixMultiply(mMatrix,mMatrix);
                newValueOfn = newValueOfn/2;
                return checkForNewValueOfn(newValueOfn,m2Matrix);
            }
            else //odd
            {
                m2Matrix = matrixMultiply(mMatrix,mMatrix);
                printMatrix(m2Matrix);
                newValueOfn = (newValueOfn-1)/2;
                return matrixMultiply(mMatrix,checkForNewValueOfn(newValueOfn,m2Matrix));
            }   
    }
    public static void main(String args[])
    {
        n = input.nextLong(); // Height of the tower
        k = input.nextLong(); // Number of available Bricks
        long availableBlocks[] = new long[(int)k]; //Array of available bricks
        
        setAvailableBlocks(availableBlocks);
        if(n<=15) //For a small number
        {
            forNLTE15(n, availableBlocks);
            long answer = ((dpMatrix[0][dpMatrix[0].length-1])*2)%(long)(Math.pow(10, 9)+7);
            System.out.println(answer);
        }
        else //For numbers greater than 15
        {
            newValueOfn = n-15;
            forNLTE15(15, availableBlocks);
            long[][] mMatrix = {{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
                               {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
                               {0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
                               {0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
                               {0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
                               {0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
                               {0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
                               {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0},
                               {0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
                               {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0},
                               {0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
                               {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
                               {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
                               {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                               {arrayH[14],arrayH[13],arrayH[12],arrayH[11],arrayH[10],arrayH[9],
                                arrayH[8],arrayH[7],arrayH[6],arrayH[5],arrayH[4],arrayH[3],
                                arrayH[2],arrayH[1],arrayH[0]}};         
            vectorV = new long[arrayH.length];
            for(int j=0;j<15;j++)
                vectorV[j] = dpMatrix[0][j+1];
            long[][] ans = checkForNewValueOfn(newValueOfn, mMatrix);
            RMatrix = matrixMultiply1X2(vectorV, ans);
            //expBySquaring(n);
            long answer =(RMatrix[RMatrix.length-1])*2;
            answer = answer %(long)(Math.pow(10, 9)+7);
            System.out.println(answer);
        }       
    }
}