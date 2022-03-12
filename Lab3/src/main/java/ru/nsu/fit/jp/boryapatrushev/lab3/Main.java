package ru.nsu.fit.jp.boryapatrushev.lab3;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC, "HelloWorld", null, "java/lang/Object", null);

        MethodVisitor mvc = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);

        mvc.visitCode();
        //push this on top ot the stack and call constructor
        mvc.visitVarInsn(Opcodes.ALOAD, 0);
        mvc.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mvc.visitInsn(Opcodes.RETURN);

        mvc.visitMaxs(0, 0);

        MethodVisitor mvm = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);

        mvm.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");

        mvm.visitLdcInsn("Hello, World!");

        mvm.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        mvm.visitInsn(Opcodes.RETURN);
        mvm.visitMaxs(0, 0);
        mvm.visitEnd();


        FileOutputStream out = new FileOutputStream("HelloWorld.class");
        out.write(cw.toByteArray());
        out.close();

    }
}
