/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package com.others.algoproject;
import java.util.Arrays;
import java.util.Scanner;
/**
 *
 * @author sanjanaagarwal
 */
public class TowersWithStrassenFinal {

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
        Since Strassen works for 2^ some power, we add extra padding to Strassen.
        Add a row and column of zeros.
    */
    static long[][] extraPadding(long[][] a)
    {
        long paddedMatrix[][] = new long[16][16]; 
        for(int i=0;i<15;i++)
        {
            for(int j=0;j<15;j++)
            {
                paddedMatrix[i][j] = a[i][j];
            }
            paddedMatrix[i][15] = 0;
        }
        for(int i=0;i<1;i++)
            for(int j=0;j<paddedMatrix.length;j++)
                paddedMatrix[15][j] = 0;
        return paddedMatrix;
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
            result = RecurMatMul(result, a);
        }   
        return result;
    }
    static void printMatrix(long[][] matrix)
    { }
    /*
        Remove the extra column and row after Strassen is executed.
    */
    static long[][] removeExtraPadding(long[][] a)
    {
        long removedPadding[][] = new long[15][15];
        for(int i=0;i<15;i++)
        {    for(int j=0;j<15;j++)
               removedPadding[i][j] = a[i][j];
        }
        return removedPadding;    
    }
    /*
        Calculation of R, where R = M*V.
    */
    static long[] matrixMultiply1X2(long[] VectorV, long[][] m2Matrix)
    {
        m2Matrix = removeExtraPadding(m2Matrix);
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
    // Strassen Multiplication
    static long[][] RecurMatMul(long a[][],long b[][])
    {
        //s1 is starting pos of 
        if(a.length==2)
        {
            long ans[][]=new long[2][2];
            long m1=((a[0][0]+a[1][1])%(long)(Math.pow(10, 9)+7)
                    *(b[0][0]+b[1][1])%(long)(Math.pow(10, 9)+7))%(long)(Math.pow(10, 9)+7);
            long m2=((a[1][0]+a[1][1])%(long)(Math.pow(10, 9)+7)
                    *b[0][0]%(long)(Math.pow(10, 9)+7))%(long)(Math.pow(10, 9)+7);
            long m3=(a[0][0]%(long)(Math.pow(10, 9)+7)
                    *(b[0][1]-b[1][1])%(long)(Math.pow(10, 9)+7))%(long)(Math.pow(10, 9)+7);
            long m4=(a[1][1]%(long)(Math.pow(10, 9)+7)
                    *(b[1][0]-b[0][0])%(long)(Math.pow(10, 9)+7))%(long)(Math.pow(10, 9)+7);
            long m5=((a[0][0]+a[0][1])%(long)(Math.pow(10, 9)+7)
                    *b[1][1]%(long)(Math.pow(10, 9)+7))%(long)(Math.pow(10, 9)+7);
            long m6=((a[1][0]-a[0][0])%(long)(Math.pow(10, 9)+7)
                    *(b[0][0]+b[0][1])%(long)(Math.pow(10, 9)+7))%(long)(Math.pow(10, 9)+7);
            long m7=((a[0][1]-a[1][1])%(long)(Math.pow(10, 9)+7)
                    *(b[1][0]+b[1][1])%(long)(Math.pow(10, 9)+7))%(long)(Math.pow(10, 9)+7);
            ans[0][0]=(m1+m4-m5+m7)%(long)(Math.pow(10, 9)+7);
            ans[0][1]=(m3+m5)%(long)(Math.pow(10, 9)+7);
            ans[1][0]=(m2+m4)%(long)(Math.pow(10, 9)+7);
            ans[1][1]=(m1-m2+m3+m6)%(long)(Math.pow(10, 9)+7);
            return ans;
        }
        else
        {
            int d=a.length/2;
            //make total 4 subarrays of a and 4 subarrays of b
            long a11[][]=make(a,0,0,d);
            long a12[][]=make(a,0,d,d);
            long a21[][]=make(a,d,0,d);
            long a22[][]=make(a,d,d,d);
            
            long b11[][]=make(b,0,0,d);
            long b12[][]=make(b,0,d,d);
            long b21[][]=make(b,d,0,d);
            long b22[][]=make(b,d,d,d);
            
            
            long m1[][]=RecurMatMul(add(a11,a22,d), add(b11,b22,d));
            long m2[][]=RecurMatMul(add(a21,a22,d), b11);
            long m3[][]=RecurMatMul(a11,sub(b12,b22,d));
            long m4[][]=RecurMatMul(a22,sub(b21,b11,d));
            long m5[][]=RecurMatMul(add(a11,a12,d), b22);
            long m6[][]=RecurMatMul(sub(a21,a11,d), add(b11,b12,d));
            long m7[][]=RecurMatMul(sub(a12, a22, d),add(b21,b22,d));
            
            long c11[][]=sub(add(add(m1,m4,d),m7,d), m5, d);
            long c12[][]=add(m3,m5,d);
            long c21[][]=add(m2,m4,d);
            long c22[][]=sub(add(add(m1,m3,d),m6,d), m2, d);
            long c[][]=new long [a.length][a.length];
            c=merge(c,c11,0,0,d);
            c=merge(c, c12, 0, d, d);
            c=merge(c, c21, d, 0, d);
            c=merge(c, c22, d, d, d);
            return c;
        }
    }
    //  merge c11,c12,c22,c21 into final c array!
    static long [][] merge(long c[][],long c1[][],int rowBeg,int colBeg,long d)
    {
        for(int i=0;i<d;i++)
        {
            for(int j=0;j<d;j++)
            {
                c[rowBeg+i][colBeg+j]=c1[i][j]%(long)(Math.pow(10, 9)+7);
            }
        }
        return c;      
    }
    //Add function
    static long [][] add(long a[][],long b[][],int d)
    {
        long ans[][]=new long[d][d];
        for(int i=0;i<d;i++)
        {
            for(int j=0;j<d;j++)
            {
                ans[i][j]=((a[i][j])%(long)(Math.pow(10, 9)+7)+(b[i][j])%(long)(Math.pow(10, 9)+7))%(long)(Math.pow(10, 9)+7);
            }
        }
        return ans;
    }
    //Subtract function
    static long[][] sub(long a[][],long b[][],int d)
    {
        long ans[][]=new long[d][d];
        for(int i=0;i<d;i++)
        {
            for(int j=0;j<d;j++)
            {
                ans[i][j]=((((a[i][j])%(long)(Math.pow(10, 9)+7)-(b[i][j])%(long)(Math.pow(10, 9)+7))
                        %(long)(Math.pow(10, 9)+7)) + (long)(Math.pow(10, 9)+7))%(long)(Math.pow(10, 9)+7);
            }
        }
        return ans;
    }
    //Copying elements
    static long[][] make(long a[][],int rowBeg,int colBeg,int howMuchToCopy)
    {
        int d=howMuchToCopy;
        long copy[][]=new long[d][d];
        for(int i=0;i<copy.length;i++)
        {
            for(int j=0;j<copy.length;j++)
            {
                copy[i][j]=a[rowBeg+i][colBeg+j]%(long)(Math.pow(10, 9)+7);
            }
        }   
        return copy;   
    }
    /*
        Exponentiation by squaring
    */
    static long checkForNewValueOfn(long newValueOfn, long[][] mMatrix)
    {
        mMatrix = extraPadding(mMatrix);
        mMatrix = extraPadding(mMatrix);
        if(newValueOfn == 1)
            {
                m2Matrix =  mMatrix;
            }
            else if(newValueOfn % 2 == 0) //even
            {
                m2Matrix = RecurMatMul(mMatrix,mMatrix);
                newValueOfn = newValueOfn/2;
                m2Matrix = powerMatrixMultiply(m2Matrix,newValueOfn);
            }
            else //odd
            {
                m2Matrix = RecurMatMul(mMatrix,mMatrix);
                newValueOfn = (newValueOfn-1)/2;
                m2Matrix = powerMatrixMultiply(m2Matrix, newValueOfn);
                m2Matrix = RecurMatMul(mMatrix,m2Matrix);
            }
            RMatrix = matrixMultiply1X2(vectorV, m2Matrix);
            return (RMatrix[RMatrix.length-1])*2;
            
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
            System.out.println((dpMatrix[0][dpMatrix[0].length-1])*2);
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
            long answer = checkForNewValueOfn(newValueOfn, mMatrix);
            answer = answer %(long)(Math.pow(10, 9)+7);
            System.out.println(answer); 
        }       
    }    
}