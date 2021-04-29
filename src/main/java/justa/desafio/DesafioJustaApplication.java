package justa.desafio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.script.ScriptEngineManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

@SpringBootApplication
@RestController
public class DesafioJustaApplication {
	Response resp;
	String equationNumbers;

	public static void main(String[] args) {
		SpringApplication.run(DesafioJustaApplication.class, args);
	}

	@GetMapping("/units/si")
	public Response convertUnits(@RequestParam(value = "units") String units) throws ScriptException {
		if (units == null)
			return new Response("", "");

		resp = new Response();
		formatUnit(units);

		return resp;
	}

	private void formatUnit(String units) throws ScriptException {
		
        String temp = replaceName(units);
		
        if (temp == units) temp = replaceSymbol(units);
		resp.setUnit_name(temp);
		
		if (temp == units) {
			resp.setMultiplication_factor("");
			return;
		}
		
		setMuiltplicationFactor();
	}

	private void setMuiltplicationFactor() throws ScriptException {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
		df.setMaximumFractionDigits(40);
		
		resp.setMultiplication_factor(formatDecimalPlaces(df.format(engine.eval(equationNumbers))));
	}
	
	
	private String formatDecimalPlaces(String doubleTxt) {
		if (!doubleTxt.contains(",")) return doubleTxt;
			
		String[] arr = doubleTxt.split(",");
		String doubleAux = arr[0] + ",";
		
		boolean validNumberFound = false;
		int count = 0;
		
		for (char decimal : arr[1].toCharArray()) {
			if (count < 14) doubleAux += decimal;
			if (decimal != '0') validNumberFound = true;
			if (validNumberFound) count++;
		}
		doubleAux = doubleAux.replace(".", "");
		
		return doubleAux.replace(",", ".");
	}

	
	private String replaceName(String equationNumbers) {
		String equationText = equationNumbers;

		if (equationNumbers.contains("minute")) {
			equationText = equationText.replace("minute", "s");
			equationNumbers = equationNumbers.replace("minute", "60");

		}

		if (equationNumbers.contains("hour")) {
			equationText = equationText.replace("hour", "s");
			equationNumbers = equationNumbers.replace("hour", "3600");

		}

		if (equationNumbers.contains("day")) {
			equationText = equationText.replace("day", "s");
			equationNumbers = equationNumbers.replace("day", "86400");
		}
		
		if (equationNumbers.contains("degree")) {
			equationText = equationText.replace("degree", "rad");
			equationNumbers = equationNumbers.replace("degree", "0.0174532925199433");
		}

		if (equationNumbers.contains("arcminute")) {
			equationText = equationText.replace("arcminute", "rad");
			equationNumbers = equationNumbers.replace("arcminute", "0.0002908882086657");
		}

		if (equationNumbers.contains("arcsecond")) {
			equationText = equationText.replace("arcsecond", "rad");
			equationNumbers = equationNumbers.replace("arcsecond", "0.0000048481368111");
		}

		if (equationNumbers.contains("hectare")) {
			equationText = equationText.replace("hectare", "m²");
			equationNumbers = equationNumbers.replace("hectare", "100000000");
		}

		if (equationNumbers.contains("litre")) {
			equationText = equationText.replace("litre", "m³");
			equationNumbers = equationNumbers.replace("litre", "0.000000001");
		}

		if (equationNumbers.contains("tonne")) {
			equationText = equationText.replace("tonne", "kg");
			equationNumbers = equationNumbers.replace("tonne", "1000");
		}
		
		this.equationNumbers = equationNumbers;
		return equationText;
	}

	private String replaceSymbol(String equationNumbers) {
		String equationText = equationNumbers;

		if (equationNumbers.contains("min")) {
			equationText = equationText.replace("min", "s");
			equationNumbers = equationNumbers.replace("min", "60");
		}
		
		if (equationNumbers.contains("ha")) {
			equationText = equationText.replace("ha", "m²");
			equationNumbers = equationNumbers.replace("ha", "100000000");
		}

		if (equationNumbers.contains("h")) {
			equationText = equationText.replace("h", "s");
			equationNumbers = equationNumbers.replace("h", "3600");
		}

		if (equationNumbers.contains("d")) {
			equationText = equationText.replace("d", "s");
			equationNumbers = equationNumbers.replace("d", "86400");
		}

		if (equationNumbers.contains("°")) {
			equationText = equationText.replace("°", "rad");
			equationNumbers = equationNumbers.replace("°", "0.0174532925199433");
		}

		if (equationNumbers.contains("'")) {
			equationText = equationText.replace("'", "rad");
			equationNumbers = equationNumbers.replace("'", "0.0002908882086657");
		}

		if (equationNumbers.contains("''")) {
			equationText = equationText.replace("''", "rad");
			equationNumbers = equationNumbers.replace("''", "0.0000048481368111");
		}

		if (equationNumbers.contains("L")) {
			equationText = equationText.replace("L", "m³");
			equationNumbers = equationNumbers.replace("L", "0.000000001");
		}

		if (equationNumbers.contains("t")) {
			equationText = equationText.replace("t", "kg");
			equationNumbers = equationNumbers.replace("t", "1000");
		}
		
		this.equationNumbers = equationNumbers;
		return equationText;
	}
}
