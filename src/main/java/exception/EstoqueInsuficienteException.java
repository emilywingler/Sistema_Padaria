/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package exception;

/**
 *
 * @author emily
 */
public class EstoqueInsuficienteException extends Exception {

    /**
     * Creates a new instance of <code>EstoqueInsuficienteException</code>
     * without detail message.
     */
    public EstoqueInsuficienteException() {
    }

    /**
     * Constructs an instance of <code>EstoqueInsuficienteException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public EstoqueInsuficienteException(String msg) {
        super(msg);
    }
}
