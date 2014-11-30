package br.com.caelum.argentum.modelo;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import br.com.caelum.argentum.mb.IFila;

public class FilaPredict<T> implements IFila<T> {
	public List<T> fila = Collections.synchronizedList(new LinkedList<T>());
	
	@Override
	public synchronized void inserir(T objeto){
		
		if(fila.size()>getTamanho())
			remove();
		
		fila.add(objeto);
	}
	
	/* (non-Javadoc)
	 * @see br.com.caelum.argentum.mb.IFila#remove()
	 */
	@Override
	public synchronized T remove(){
		return fila.remove(0);
	}
	
	/* (non-Javadoc)
	 * @see br.com.caelum.argentum.mb.IFila#vazia()
	 */
	@Override
	public synchronized boolean vazia(){
		return fila.size()==0;
	}

	/* (non-Javadoc)
	 * @see br.com.caelum.argentum.mb.IFila#poll()
	 */
	@Override
	public synchronized T poll() {
		
		return fila.get(fila.size()-1);
	}
	
	/* (non-Javadoc)
	 * @see br.com.caelum.argentum.mb.IFila#getItensFila()
	 */
	@Override
	public synchronized List<T> getItensFila(){
		return fila;
	}

	/* (non-Javadoc)
	 * @see br.com.caelum.argentum.mb.IFila#getFila()
	 */
	@Override
	public synchronized List<T> getFila() {
		return fila;
	}

	@Override
	public int getTamanho() {
		// TODO Auto-generated method stub
		return 1510;
	}

}
