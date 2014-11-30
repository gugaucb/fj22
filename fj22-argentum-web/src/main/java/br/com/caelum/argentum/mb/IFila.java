package br.com.caelum.argentum.mb;

import java.util.List;

public interface IFila<T> {

	public abstract void inserir(T objeto);

	public abstract T remove();

	public abstract boolean vazia();

	public abstract T poll();

	public abstract List<T> getItensFila();

	public abstract List<T> getFila();
	
	public abstract int getTamanho();

}