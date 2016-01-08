package br.com.lefranchi.eventz.service;

import br.com.lefranchi.eventz.domain.Producer;

public interface RouteService {

	public void loadRoutes();

	void loadInternalRoute(final Producer producer) throws Exception;

	void loadExternalRoute(Producer producer) throws Exception;

}
