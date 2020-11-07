package net.ausiasmarch.trolleyesSBserver.api;

import java.util.List;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.trolleyesSBserver.bean.ResponseBean;
import net.ausiasmarch.trolleyesSBserver.entity.TipousuarioEntity;
import net.ausiasmarch.trolleyesSBserver.repository.TipousuarioRepository;
import net.ausiasmarch.trolleyesSBserver.service.FillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tipousuario")
public class TipousuarioController {

    @Autowired
    HttpSession oHttpSession;

    @Autowired
    TipousuarioRepository oTipousuarioRepository;

    @Autowired
    FillService oFillService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<TipousuarioEntity>(oTipousuarioRepository.getOne(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> all() {
        if (oTipousuarioRepository.count() <= 1000) {
            return new ResponseEntity<List<TipousuarioEntity>>(oTipousuarioRepository.findAll(), HttpStatus.OK);
        } else {
            ResponseBean oSessionBean = new ResponseBean();
            oSessionBean.setMessage("ERROR: TO MANY REGISTRIES");
            oSessionBean.setStatus(500);
            return new ResponseEntity<ResponseBean>(oSessionBean, HttpStatus.OK);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> count() {
        return new ResponseEntity<Long>(oTipousuarioRepository.count(), HttpStatus.OK);
    }

    @PostMapping("/fill")
    public ResponseEntity<?> fill() {
        oFillService.tipousuarioFill();
        ResponseBean oSessionBean = new ResponseBean();
        oSessionBean.setMessage("OK");
        oSessionBean.setStatus(200);
        return new ResponseEntity<ResponseBean>(oSessionBean, HttpStatus.OK);

    }
}
