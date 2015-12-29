package br.com.lefranchi.eventz.service;

import br.com.lefranchi.eventz.domain.Producer;

public interface RouteService {

	public void loadRoutes();

	void loadRoute(final Producer producer);

}
