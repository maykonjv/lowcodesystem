/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lowcodesystem.ws;

//import javax.jws.WebService;
//import javax.jws.WebMethod;
//import javax.jws.WebParam;
//import javax.jws.soap.SOAPBinding;
//import javax.jws.soap.SOAPBinding.Style;

//@WebService
//@SOAPBinding(style = Style.RPC)
public interface CalculadoraServer {

//    @WebMethod
    float soma(
//            @WebParam(name = "valor1") 
                    float num1, 
//            @WebParam(name = "valor2") 
                    float num2);

//    @WebMethod
    float subtracao(float num1, float num2);

//    @WebMethod
    float multiplicacao(float num1, float num2);

//    @WebMethod
    float divisao(float num1, float num2);
}
