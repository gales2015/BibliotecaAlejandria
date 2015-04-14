package org.alexandrialibrary.spring.service.impl;

import java.util.List;
import java.util.Locale;

import org.alexandrialibrary.spring.bean.Prestamo;
import org.alexandrialibrary.spring.dao.PrestamoDAO;
import org.alexandrialibrary.spring.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PrestamoServiceImpl implements PrestamoService {

	@Autowired
    private PrestamoDAO prestamoDAO;
	
	@Override
	public List<Prestamo> getAllPrestamos() {
		return prestamoDAO.getAllPrestamos();
	}

	@Override
	public Prestamo getPrestamo(long id) {
		return prestamoDAO.getPrestamo(id);
	}

	@Override
	public List<Prestamo> getPrestamosByDevuelto(boolean isDevuelto) {
		return prestamoDAO.getPrestamosByDevuelto(isDevuelto);
	}

	@Override
	public long save(Prestamo prestamo, Locale locale) {
		return prestamoDAO.save(prestamo, locale);
	}

	@Override
	public void update(Prestamo prestamo) {
		prestamoDAO.update(prestamo);
	}

	@Override
	public void delete(long id) {
		prestamoDAO.delete(id);
	}

}
