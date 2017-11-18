package com.xpt.vuforia.SampleApplication.utils;

import java.nio.Buffer;

public class TextPlane extends MeshObject {
	
	private final static double planeVertices[] =
        {
                -50f, -50f, 0.0f, 50f, -50f, 0.0f, 50f, 50f, 0.0f, -50f, 50f, 0.0f
        };
	
	 private final static double planeTexcoords[] =
	        {
	                0, 0, 1, 0, 1, 1, 0, 1
	        };
	  private final static double planeNormals[] =
	        {
	                0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1
	        };
	  private final static short planeIndices[] =
	        {
	                0, 1, 2, 0, 2, 3
	        };


	  private Buffer mVertBuff;
	  private Buffer mTexCoordBuff;
	  private Buffer mNormBuff;
	  private Buffer mIndBuff;

	  public TextPlane(){
	    mVertBuff = fillBuffer(planeVertices);
	    mTexCoordBuff = fillBuffer(planeTexcoords);
	    mNormBuff = fillBuffer(planeNormals);
	    mIndBuff = fillBuffer(planeIndices);
	  }

	  @Override
	  public Buffer getBuffer(BUFFER_TYPE bufferType) {
	    Buffer result = null;
	    switch (bufferType)
	    {
	        case BUFFER_TYPE_VERTEX:
	            result = mVertBuff;
	            break;
	        case BUFFER_TYPE_TEXTURE_COORD:
	            result = mTexCoordBuff;
	            break;
	        case BUFFER_TYPE_INDICES:
	            result = mIndBuff;
	            break;
	        case BUFFER_TYPE_NORMALS:
	            result = mNormBuff;
	        default:
	            break;
	     }
	    return result;
	  }

	  @Override
	  public int getNumObjectVertex() {
	      return planeVertices.length / 3;
	  }

	  @Override
	  public int getNumObjectIndex() {
	      return planeIndices.length;
	  }
}

