/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CA2;

/**
 *
 * @author D00133809
 */
public class Convolve {
    
    public Convolve()
    {
        super();
    }
    
    
    
     private final float noConvolveMatrix[] =
        {
            0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f
        };
        private final float sharpenMatrix[] =
        {
            -1.0f, -1.0f, -1.0f,
            -1.0f, 9.0f, -1.0f,
            -1.0f, -1.0f, -1.0f
        };          // adds up to 1
        private final float blurMatrix[] =
        {
            0.1111f, 0.1111f, 0.1111f,
            0.1111f, 0.1111f, 0.1111f,
            0.1111f, 0.1111f, 0.1111f
        };   // each element is 1/9
        private final float edgeDetectMatrix[] =
        {
            -0.5f, -0.5f, -0.5f,
            -0.5f, 4.0f, -0.5f,
            -0.5f, -0.5f, -0.5f
        };          // adds up to 0
        private final float lightMatrix[] =
        {
            0.1f, 0.1f, 0.1f,
            0.1f, 1.0f, 0.1f,
            0.1f, 0.1f, 0.1f
        };          // adds to greater than 0
        private final float darkMatrix[] =
        {
            0.01f, 0.01f, 0.01f,
            0.01f, 0.5f, 0.01f,
            0.01f, 0.01f, 0.01f
        };          // adds to less than 0
        
        private final float embossMatrix[] =
        {
            0,  0,  0, 
            0,  2, -1,
            0, -1,  0
        };
        
        private final float convolveMatrices[][] =
    {
        this.noConvolveMatrix, this.sharpenMatrix, this.blurMatrix,
        this.edgeDetectMatrix, this.lightMatrix, this.darkMatrix, this.embossMatrix
    };
        
        public float[] getConvMatrix(int num)
        {
            return convolveMatrices[num];
        }
    
}
